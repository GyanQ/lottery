package com.vietnam.lottery.business.unpackRedPackets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackAddRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackDeleteRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackListRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackUpdateRequest;
import com.vietnam.lottery.business.unpackRedPackets.response.BroadcastResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackDetailResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackListResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnpackLotteryResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.List;

/**
 * 拆红包(UnpackRedPackets)表服务接口
 *
 * @author Gyan
 * @since 2022-02-16 18:00:37
 */
public interface UnpackRedPacketsService {

    /* 新增 */
    ResultModel add(UnPackAddRequest request);

    /* 修改 */
    ResultModel update(UnPackUpdateRequest request);

    /* 删除 */
    ResultModel delete(UnPackDeleteRequest request);

    /* 详情 */
    UnPackDetailResponse detail(Long id);

    /* 列表 */
    Page<UnPackListResponse> list(UnPackListRequest request);

    /* 抽奖 */
    UnpackLotteryResponse lottery(String userId);

    //广播  true代表只查询一条
    List<BroadcastResponse> broadcast(Boolean flag);
}

