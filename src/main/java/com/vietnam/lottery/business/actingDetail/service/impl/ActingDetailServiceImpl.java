package com.vietnam.lottery.business.actingDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingDetail.mapper.ActingDetailMapper;
import com.vietnam.lottery.business.actingDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingDetail.service.ActingDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 代理详情(ActingDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 11:41:31
 */
@Service("actingDetailService")
public class ActingDetailServiceImpl implements ActingDetailService {
    @Resource
    private ActingDetailMapper actingDetailMapper;

    @Override
    public Page<ActingDetailListResponse> list(ActingDetailListRequest request) {
        Page<ActingDetailListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return actingDetailMapper.list(page, request);
    }
}

