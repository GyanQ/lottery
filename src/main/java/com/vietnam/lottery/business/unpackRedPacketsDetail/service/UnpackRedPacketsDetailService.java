package com.vietnam.lottery.business.unpackRedPacketsDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;

/**
 * 拆红包明细(UnpackRedPacketsDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-04-05 09:49:58
 */
public interface UnpackRedPacketsDetailService {
    /* 开奖记录 */
    Page<LotteryListResponse> list(LotteryListRequest request);
}

