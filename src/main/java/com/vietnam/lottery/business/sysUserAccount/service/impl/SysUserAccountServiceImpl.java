package com.vietnam.lottery.business.sysUserAccount.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.mapper.SysBankCardMapper;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.*;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
import com.vietnam.lottery.common.config.PaymentUtils;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账户明细(SysUserAccount)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-05 22:30:50
 */
@Service("sysUserAccountService")
public class SysUserAccountServiceImpl implements SysUserAccountService {
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;
    @Resource
    private RechargeDetailMapper rechargeDetailMapper;
    @Resource
    private SysBankCardMapper sysBankCardMapper;

    @Override
    public Page<UserLotteryListResponse> lotteryList(UserLotteryListRequest request) {
        Page<UserLotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserAccountMapper.lotteryList(page, request);
    }

    @Override
    public Page<WithdrawListResponse> withdrawList(WithdrawListRequest request) {
        Page<UserLotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserAccountMapper.withdrawList(page, request);
    }

    @Override
    public Page<CommissionListResponse> commissionsList(CommissionListRequest request) {
        Page<UserLotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        Page<CommissionListResponse> iPage = sysUserAccountMapper.commissionsList(page, request);
        if (CollectionUtils.isEmpty(iPage.getRecords())) return iPage;
        for (CommissionListResponse o : iPage.getRecords()) {
            SubordinateListListRequest subordinate = new SubordinateListListRequest();
            subordinate.setUserId(o.getUserId());
            List<SubordinateListListResponse> list = subordinateList(subordinate);
            o.setAmount(list.stream().map(SubordinateListListResponse::getRechargeAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            o.setCommissionAmount(list.stream().map(SubordinateListListResponse::getCommissionAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        return iPage;
    }

    //保存递归对象
    List<SubordinateListListResponse> list = new ArrayList<>();

    @Override
    public List<SubordinateListListResponse> subordinateList(SubordinateListListRequest request) {
        list = myLowerLevel(request.getUserId());
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel withdrawAudit(WithdrawAuditRequest request) {
        SysUserAccount userAccount = sysUserAccountMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(userAccount)) throw new GlobalException("fail to edit");

        //用户支入金额
        BigDecimal incomeAmount = BigDecimal.ZERO;
        //用户支出金额
        BigDecimal expenditureAmount = BigDecimal.ZERO;
        //提现余额
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        //查询用户余额
        Map<String, Map<String, Object>> map = sysUserAccountMapper.getByIdAmount(request.getCreateBy());
        for (Map<String, Object> value : map.values()) {
            incomeAmount = (BigDecimal) value.get("incomeAmount");
            expenditureAmount = (BigDecimal) value.get("expenditureAmount");
            withdrawAmount = (BigDecimal) value.get("withdrawAmount");
        }
        //用户充值余额
        BigDecimal rechargeTotal = rechargeDetailMapper.getByIdRecharge(request.getCreateBy());
        //用户总余额
        BigDecimal userAmount = rechargeTotal.add(incomeAmount).subtract(expenditureAmount).subtract(withdrawAmount);
        if (userAmount.compareTo(userAccount.getAmount()) == -1) {
            throw new GlobalException("Withdrawal failed due to insufficient user balance");
        }
        SysBankCard bankCard = sysBankCardMapper.selectById(userAccount.getBankCardId());
        if (ObjectUtil.isEmpty(bankCard)) throw new GlobalException("Can't find bank card information");
        String payload = bankCard.getCollectionName() + bankCard.getCardNum() + bankCard.getCardSerialNum() + bankCard.getBackName();

        //发起提现
        CreateWithdrawRequest createWithdrawRequest = new CreateWithdrawRequest();
        createWithdrawRequest.setPayload(payload);
        createWithdrawRequest.setAmount(userAccount.getAmount());
        createWithdrawRequest.setOrderNo(userAccount.getId());
        PaymentUtils.createWithdraw(createWithdrawRequest);

        //更新提现明细
        userAccount.setAudit(request.getAudit());
        userAccount.setUpdateBy(request.getCreateBy());
        userAccount.setUpdateDate(new Date());
        return ResultUtil.success(sysUserAccountMapper.updateById(userAccount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel withdraw(WithdrawRequest request) {
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setType("3");
        sysUserAccount.setSpending("1");
        sysUserAccount.setAmount(request.getAmount());
        sysUserAccount.setAudit("1");
        sysUserAccount.setBankCardId(request.getBankCardId());
        sysUserAccount.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysUserAccountMapper.insert(sysUserAccount));
    }

    @Override
    public AccountBalanceResponse accountBalance(String userId) {
        AccountBalanceResponse response = sysUserAccountMapper.getByIdAmountDetail(userId);
        BigDecimal spendingAmount = response.getCommissionBalanceAmount().add(response.getRedEnvelopeAmount()).add(response.getRechargeAmount());
        BigDecimal expenditure = response.getWithdrawalAmount().add(response.getGrabAmount());
        response.setAmount(spendingAmount.subtract(expenditure));
        return response;
    }

    @Override
    public Page<CommissionLDetailResponse> commissionDetails(CommissionLDetailRequest request) {
        Page<CommissionLDetailResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserAccountMapper.commissionDetails(page, request);
    }

    @Override
    public Page<WithdrawDetailResponse> withdrawDetail(WithdrawDetailRequest request) {
        Page<WithdrawDetailResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserAccountMapper.withdrawDetail(page, request);
    }

    /* 递归查询下级代理用户 */
    private List<SubordinateListListResponse> myLowerLevel(String id) {
        List<SubordinateListListResponse> listResponses = sysUserAccountMapper.lowerLevelList(id);
        if (CollectionUtils.isEmpty(listResponses)) return list;
        list.addAll(listResponses);
        for (SubordinateListListResponse o : listResponses) {
            myLowerLevel(o.getUserId());
        }
        return list;
    }
}

