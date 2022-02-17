package com.vietnam.lottery.business.lotteryDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.lotteryDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.lotteryDetail.response.LotteryListResponse;

/**
 * 开奖记录(LotteryDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-17 12:17:14
 */
public interface LotteryDetailService {

    /* 开奖记录 */
    Page<LotteryListResponse> list(LotteryListRequest request);
}

