package com.vietnam.lottery.business.rechargeDetail.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;

/**
 * 订单(Order)表服务接口
 *
 * @author Gyan
 * @since 2022-03-01 14:49:23
 */
public interface RechargeDetailService {
    /*  充值记录 */
    Page<RechargeListResponse> list(RechargeListRequest request);

    //充值
    JSONObject pay(PayRequest request);

    //回调
    void callBack(String body);
}

