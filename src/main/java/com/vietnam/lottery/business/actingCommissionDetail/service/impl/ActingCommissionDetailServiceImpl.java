package com.vietnam.lottery.business.actingCommissionDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.mapper.ActingCommissionDetailMapper;
import com.vietnam.lottery.business.actingCommissionDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.LowerLevelListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.service.ActingCommissionDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 代理详情(ActingCommissionDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 11:41:31
 */
@Service("actingDetailService")
public class ActingCommissionDetailServiceImpl implements ActingCommissionDetailService {
    @Resource
    private ActingCommissionDetailMapper actingCommissionDetailMapper;

    @Override
    public Page<ActingDetailListResponse> list(ActingDetailListRequest request) {
        Page<ActingDetailListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return actingCommissionDetailMapper.list(page, request);
    }

    @Override
    public Page<LowerLevelListResponse> lowerLevelList(LowerLevelListRequest request) {
        Page<LowerLevelListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return actingCommissionDetailMapper.lowerLevelList(page, request);
    }
}

