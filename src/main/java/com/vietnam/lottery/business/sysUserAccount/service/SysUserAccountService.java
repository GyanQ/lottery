package com.vietnam.lottery.business.sysUserAccount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.*;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.List;

/**
 * 账户明细(SysUserAccount)表服务接口
 *
 * @author Gyan
 * @since 2022-04-05 22:30:49
 */
public interface SysUserAccountService {

    //开奖列表
    Page<UserLotteryListResponse> lotteryList(UserLotteryListRequest request);

    //提现列表
    Page<WithdrawListResponse> withdrawList(WithdrawListRequest request);

    //用户分佣列表
    Page<CommissionListResponse> commissionsList(CommissionListRequest request);

    //下级代理列表
    List<SubordinateListListResponse> subordinateList(SubordinateListListRequest request);

    //提现审核
    ResultModel withdrawAudit(WithdrawAuditRequest request);

    //提现
    ResultModel withdraw(WithdrawRequest request,String language);

    /* 账户余额 */
    AccountBalanceResponse accountBalance(String userId);

    //根据userId查询分佣明细
    Page<CommissionLDetailResponse> commissionDetails(CommissionLDetailRequest request);

    //根据userId查询提现记录
    Page<WithdrawDetailResponse> withdrawDetail(WithdrawDetailRequest request);

    //提现回调
    void callBack(String body);

    //根据userId查询拆红包数量
    Long envelopeCount(String userId);
}

