package com.vietnam.lottery.business.rechargeDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 充值记录(RechargeDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-17 11:48:02
 */
public interface RechargeDetailService {

    /*  充值记录 */
    Page<RechargeListResponse> list(RechargeListRequest request);

    //充值
    ResultModel pay(PayRequest request);

    //回调
    void callBack(String body);
}

