package com.vietnam.lottery.business.grabRedPackets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.request.*;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 抢红包(GrabRedPackets)表服务接口
 *
 * @author Gyan
 * @since 2022-02-16 18:00:22
 */
public interface GrabRedPacketsService {

    /* 新增 */
    ResultModel add(AddRequest request);

    /* 修改 */
    ResultModel update(UpdateRequest request);

    /* 删除 */
    ResultModel delete(DeleteRequest request);

    /* 详情 */
    DetailResponse detail(Long id);

    /* 列表 */
    Page<ListResponse> list(ListRequest request);

    /* 下注 */
    ResultModel bet(BetRequest request);
}

