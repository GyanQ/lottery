package com.vietnam.lottery.business.sysUserAccount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.request.UserLotteryListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawListRequest;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户明细(SysUserAccount)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-05 22:30:48
 */
@Mapper
public interface SysUserAccountMapper extends BaseMapper<SysUserAccount> {
    //用户开奖列表
    Page<UserLotteryListResponse> lotteryList(@Param("page") Page page, @Param("request") UserLotteryListRequest request);

    //用户提现列表
    Page<WithdrawListResponse> withdrawList(@Param("page") Page page, @Param("request") WithdrawListRequest request);
}

