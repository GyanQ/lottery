package com.vietnam.lottery.business.lotteryDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.lotteryDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.lotteryDetail.request.PayRequest;
import com.vietnam.lottery.business.lotteryDetail.response.LotteryListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 开奖记录(LotteryDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-17 12:17:14
 */
public interface LotteryDetailService {

    /* 开奖记录 */
    Page<LotteryListResponse> list(LotteryListRequest request);

    //充值
    ResultModel pay(PayRequest request);
}

