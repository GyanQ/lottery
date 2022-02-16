package com.vietnam.lottery.business.actingDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingDetail.response.LowerLevelListResponse;

/**
 * 代理详情(ActingDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-16 11:41:30
 */
public interface ActingDetailService {

    /* 代理列表 */
    Page<ActingDetailListResponse> list(ActingDetailListRequest request);

    /* 下级代理列表 */
    Page<LowerLevelListResponse> lowerLevelList(LowerLevelListRequest request);
}
