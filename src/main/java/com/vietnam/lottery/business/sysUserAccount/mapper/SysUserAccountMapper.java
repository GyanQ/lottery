package com.vietnam.lottery.business.sysUserAccount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.*;
import com.vietnam.lottery.business.unpackRedPackets.response.BroadcastResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.DismantleResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.GrabResponse;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    //用户分佣列表
    Page<CommissionListResponse> commissionsList(@Param("page") Page page, @Param("request") CommissionListRequest request);

    /* 下级代理列表 */
    List<SubordinateListListResponse> lowerLevelList(@Param("id") String id);

    //根据userId查询用户支入支出
    @MapKey("createBy")
    Map<String, Map<String, BigDecimal>> getByIdAmount(@Param("userId") String userId);

    //根据userId查询用户抢红包和拆红包次数
    @MapKey("createBy")
    Map<String, Map<String, Long>> getByIdCount(@Param("userId") String userId);

    //根据userId查询用户余额
    AccountBalanceResponse getByIdAmountDetail(@Param("userId") String userId);

    //根据userId查询分佣明细
    Page<CommissionLDetailResponse> commissionDetails(@Param("page") Page page, @Param("request") CommissionLDetailRequest request);

    //根据userId查询提现记录
    Page<WithdrawDetailResponse> withdrawDetail(@Param("page") Page page, @Param("request") WithdrawDetailRequest request);

    //查询广播一条数据
    BroadcastResponse broadcast(@Param("type") String type);

    //查询广播拆红包数据
    List<DismantleResponse> selectDis();

    //查询广播抢红包数据
    List<GrabResponse> selectGrab();
}

