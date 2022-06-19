package com.vietnam.lottery.business.sysUserAccount.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.*;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
import com.vietnam.lottery.business.sysUserBankCard.entity.SysUserBankCard;
import com.vietnam.lottery.business.sysUserBankCard.mapper.SysUserBankCardMapper;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.PaymentUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SysUserAccountServiceImpl implements SysUserAccountService {
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;
    @Resource
    private RechargeDetailMapper rechargeDetailMapper;
    @Resource
    private SysUserBankCardMapper sysUserBankCardMapper;

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
        }
        return iPage;
    }

    //保存递归对象
    public static List<SubordinateListListResponse> list;

    @Override
    public List<SubordinateListListResponse> subordinateList(SubordinateListListRequest request) {
        list = new ArrayList<>();
        list = myLowerLevel(request.getUserId());
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel withdrawAudit(WithdrawAuditRequest request) {
        SysUserAccount userAccount = sysUserAccountMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(userAccount)) throw new GlobalException("查询不到提现记录");

        if ("2".equals(request.getAudit())) {
            userAccount.setAudit(request.getAudit());
            userAccount.setUpdateBy(request.getCreateBy());
            userAccount.setUpdateDate(new Date());
            return ResultUtil.success(sysUserAccountMapper.updateById(userAccount));
        }

        //用户支入金额
        BigDecimal incomeAmount = BigDecimal.ZERO;
        //用户支出金额
        BigDecimal expenditureAmount = BigDecimal.ZERO;
        //提现余额
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        //查询用户余额明细
        Map<String, Map<String, BigDecimal>> map = sysUserAccountMapper.getByIdAmount(request.getCreateBy());
        for (Map<String, BigDecimal> value : map.values()) {
            incomeAmount.add(value.get("incomeAmount"));
            expenditureAmount.add(value.get("expenditureAmount"));
            withdrawAmount.add(value.get("withdrawAmount"));
        }
        //用户充值余额
        BigDecimal rechargeTotal = rechargeDetailMapper.getByIdRecharge(request.getCreateBy());
        //用户总余额
        BigDecimal totalAmount = rechargeTotal.add(incomeAmount).subtract(expenditureAmount).subtract(withdrawAmount);
        if (totalAmount.compareTo(userAccount.getAmount()) == -1) {
            throw new GlobalException("用户余额不足,提现失败");
        }
        SysUserBankCard bankCard = sysUserBankCardMapper.selectById(userAccount.getBankCardId());
        if (ObjectUtil.isEmpty(bankCard)) throw new GlobalException("查询不到用户银行卡信息");
        String payload = bankCard.getCardName() + bankCard.getCardNo() + bankCard.getBankId() + bankCard.getBackName();

        //发起提现
        CreateWithdrawRequest createWithdrawRequest = new CreateWithdrawRequest();
        createWithdrawRequest.setPayload(payload);
        createWithdrawRequest.setAmount(userAccount.getAmount());
        createWithdrawRequest.setOrderNo(userAccount.getId());
        String str = PaymentUtils.createWithdraw(createWithdrawRequest);
        log.info("创建提现Result:{}", str);
        JSONObject json = JSONUtil.parseObj(str);
        JSONObject data = json.getJSONObject("data");
        log.info("获取data,{}", data);
        if (json.getInt("code") != 1) {
            throw new GlobalException("调用提现api失败");
        }
        //更新提现明细
        userAccount.setUpdateBy(request.getCreateBy());
        userAccount.setUpdateDate(new Date());
        return ResultUtil.success(sysUserAccountMapper.updateById(userAccount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel withdraw(WithdrawRequest request, String language) {
        SysUserBankCard userBankCard = sysUserBankCardMapper.selectById(request.getBankCardId());
        if (ObjectUtil.isEmpty(userBankCard)) {
            if ("0".equals(language)) {
                return ResultUtil.failure("No bank card result");
            } else {
                return ResultUtil.failure("Không kiểm tra được thông tin ngân hàng");
            }

        }
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setType("3");
        sysUserAccount.setSpending("1");
        sysUserAccount.setAmount(request.getAmount());
        sysUserAccount.setAudit("1");
        sysUserAccount.setBankCardId(userBankCard.getId());
        sysUserAccount.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysUserAccountMapper.insert(sysUserAccount));
    }

    @Override
    public AccountBalanceResponse accountBalance(String userId) {
        AccountBalanceResponse response = sysUserAccountMapper.getByIdAmountDetail(userId);
        //收入余额
        BigDecimal spendingAmount = response.getCommissionBalanceAmount().add(response.getRedEnvelopeAmount()).add(response.getRechargeAmount());
        //支出余额
        BigDecimal expenditure = response.getWithdrawalAmount().add(response.getGrabAmount());
        //账户总余额
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callBack(String body) {
        log.info("提现回调信息:{}", body);
        JSONObject json = JSONUtil.parseObj(body);
        JSONObject data = json.getJSONObject("data");
        log.info("获取提现回调信息data:{}", data);
        Integer isPay = data.getInt("ispay");
        log.info("获取提现回调信息ispay:{}", isPay);
        String orderNo = data.getStr("orderid");
        log.info("获取提现回调信息orderNo:{}", orderNo);
        SysUserAccount sysUserAccount = sysUserAccountMapper.selectById(orderNo);
        log.info("账户明细sysUserAccount,{}", sysUserAccount);
        if (ObjectUtil.isEmpty(sysUserAccount)) return;

        if (isPay != 1) {
            log.info("提现失败");
            return;
        }
        //更新充值记录
        sysUserAccount.setAudit("3");
        sysUserAccount.setUpdateBy(sysUserAccount.getCreateBy());
        sysUserAccount.setUpdateDate(new Date());
        sysUserAccountMapper.updateById(sysUserAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long envelopeCount(String userId) {
        log.info("envelopeCount入参:{}", userId);
        Long grabCount = 0l;
        Long unpackCount = 0l;
        Map<String, Map<String, Long>> map = sysUserAccountMapper.getByIdCount(userId);
        for (Map<String, Long> value : map.values()) {
            grabCount += value.get("grabCount");
            unpackCount += value.get("unpackCount");
        }
        Long count = 0l;
        if (unpackCount >= grabCount) {
            log.info("envelopeCount抢红包大于等于抢红包返回参数:{}", count);
            return count;
        }
        count = grabCount - unpackCount;
        log.info("envelopeCount抢红包小于抢红包返回参数:{}", count);
        return count;
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

