package com.vietnam.lottery.business.grabRedPackets.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.request.*;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    String bet(BetRequest request);

    /* 支付回调 */
    Map<String, Object> callBack(HttpServletRequest httpServletRequest);

    /* 获取订单支付二维码*/
    String selectOrderInfo(String ticket);
}

