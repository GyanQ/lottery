package com.vietnam.lottery.business.sysUserAccount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.request.CommissionListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.SubordinateListListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.UserLotteryListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawListRequest;
import com.vietnam.lottery.business.sysUserAccount.response.CommissionListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.SubordinateListListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;

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
    List<SubordinateListListResponse>  subordinateList(SubordinateListListRequest request);
}

