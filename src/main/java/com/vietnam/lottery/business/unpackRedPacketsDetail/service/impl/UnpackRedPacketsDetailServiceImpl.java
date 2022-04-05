package com.vietnam.lottery.business.unpackRedPacketsDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.unpackRedPacketsDetail.mapper.UnpackRedPacketsDetailMapper;
import com.vietnam.lottery.business.unpackRedPacketsDetail.service.UnpackRedPacketsDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拆红包明细(UnpackRedPacketsDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-05 09:49:58
 */
@Service("unpackRedPacketsDetailService")
public class UnpackRedPacketsDetailServiceImpl implements UnpackRedPacketsDetailService {
    @Resource
    private UnpackRedPacketsDetailMapper unpackRedPacketsDetailMapper;

    @Override
    public Page<LotteryListResponse> list(LotteryListRequest request) {
        Page<LotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return unpackRedPacketsDetailMapper.list(page, request);
    }
}

