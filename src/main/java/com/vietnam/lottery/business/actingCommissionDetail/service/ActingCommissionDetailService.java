package com.vietnam.lottery.business.actingCommissionDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.CommissionDetailsRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.CommissionDetailsResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.LowerLevelListResponse;

import java.util.List;

/**
 * 代理详情(ActingCommissionDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-16 11:41:30
 */
public interface ActingCommissionDetailService {

    /* 代理列表 */
    Page<ActingDetailListResponse> list(ActingDetailListRequest request);

    /* 下级代理列表 */
    List<LowerLevelListResponse> lowerLevelList(LowerLevelListRequest request);

    /* 查询当前用户分佣明细 */
    Page<CommissionDetailsResponse> commissionDetails(CommissionDetailsRequest request);
}

