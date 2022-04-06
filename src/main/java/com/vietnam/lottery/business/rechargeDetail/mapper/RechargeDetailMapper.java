package com.vietnam.lottery.business.rechargeDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 订单(Order)表数据库访问层
 *
 * @author Gyan
 * @since 2022-03-01 14:49:22
 */
@Mapper
public interface RechargeDetailMapper extends BaseMapper<RechargeDetail> {

    //充值列表
    Page<RechargeListResponse> list(@Param("page") Page page, @Param("request") RechargeListRequest request);

    //查询用户充值总余额
    BigDecimal getByIdRecharge(@Param("userId") String userId);
}

