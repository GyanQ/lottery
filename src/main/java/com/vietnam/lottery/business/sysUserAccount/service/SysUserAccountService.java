package com.vietnam.lottery.business.sysUserAccount.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.request.UserLotteryListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawListRequest;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;

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
}

