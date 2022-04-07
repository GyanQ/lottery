package com.vietnam.lottery.business.recharge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.recharge.request.RechargeAddRequest;
import com.vietnam.lottery.business.recharge.request.RechargeDeleteRequest;
import com.vietnam.lottery.business.recharge.request.RechargeListRequest;
import com.vietnam.lottery.business.recharge.request.RechargeUpdateRequest;
import com.vietnam.lottery.business.recharge.response.RechargeListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 充值配置(Recharge)表服务接口
 *
 * @author Gyan
 * @since 2022-04-07 23:28:19
 */
public interface RechargeService {

    //新增
    ResultModel add(RechargeAddRequest request);

    //修改
    ResultModel update(RechargeUpdateRequest request);

    //列表
    Page<RechargeListResponse> list(RechargeListRequest request);

    //删除
    ResultModel delete(RechargeDeleteRequest request);
}

