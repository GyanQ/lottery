package com.vietnam.lottery.business.actingCommissionDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.entity.ActingCommissionDetail;
import com.vietnam.lottery.business.actingCommissionDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.LowerLevelListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 代理详情(ActingCommissionDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 11:41:29
 */
@Mapper
public interface ActingCommissionDetailMapper extends BaseMapper<ActingCommissionDetail> {
    /* 代理列表 */
    Page<ActingDetailListResponse> list(@Param("page") Page page, @Param("request") ActingDetailListRequest request);

    /* 下级代理列表 */
    Page<LowerLevelListResponse> lowerLevelList(@Param("page") Page page, @Param("request") LowerLevelListRequest request);
}

