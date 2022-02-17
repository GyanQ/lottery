package com.vietnam.lottery.business.lotteryDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.lotteryDetail.mapper.LotteryDetailMapper;
import com.vietnam.lottery.business.lotteryDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.lotteryDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.lotteryDetail.service.LotteryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开奖记录(LotteryDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 12:17:14
 */
@Service("lotteryDetailService")
public class LotteryDetailServiceImpl implements LotteryDetailService {
    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;

    @Override
    public Page<LotteryListResponse> list(LotteryListRequest request) {
        Page<LotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return lotteryDetailMapper.list(page, request);
    }
}

