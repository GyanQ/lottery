package com.vietnam.lottery.business.actingDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingDetail.entity.ActingDetail;
import com.vietnam.lottery.business.actingDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingDetail.response.ActingDetailListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 代理详情(ActingDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 11:41:29
 */
@Mapper
public interface ActingDetailMapper extends BaseMapper<ActingDetail> {
    /* 代理列表 */
    Page<ActingDetailListResponse> list(@Param("page") Page page, @Param("request") ActingDetailListRequest request);
}

