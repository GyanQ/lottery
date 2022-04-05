package com.vietnam.lottery.business.sysUserAccount.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.CommissionListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.SubordinateListListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
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

        userAccount.setAudit(request.getAudit());
        userAccount.setUpdateBy(request.getCreateBy());
        userAccount.setUpdateDate(new Date());
        return ResultUtil.success(sysUserAccountMapper.updateById(userAccount));
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

