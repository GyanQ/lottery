package com.vietnam.lottery.business.rechargeDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 充值记录(RechargeDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-17 11:48:01
 */
@Mapper
public interface RechargeDetailMapper extends BaseMapper<RechargeDetail> {
    /* 充值记录列表 */
    Page<RechargeListResponse> list(@Param("page") Page page, @Param("request") RechargeListRequest request);
}

