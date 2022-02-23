package com.vietnam.lottery.business.withdrawDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.withdrawDetail.entity.WithdrawDetail;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawListRequest;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 提现记录(WithdrawDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-17 10:50:07
 */
@Mapper
public interface WithdrawDetailMapper extends BaseMapper<WithdrawDetail> {

    /* 提现列表 */
    Page<WithdrawListResponse> list(@Param("page") Page page, @Param("request") WithdrawListRequest request);

    /* 用户分佣提现金额 */
    BigDecimal commissionWithdraw(@Param("commissionDetailId") Long commissionDetailId, @Param("lotteryDetailId") Long lotteryDetailId);
}

