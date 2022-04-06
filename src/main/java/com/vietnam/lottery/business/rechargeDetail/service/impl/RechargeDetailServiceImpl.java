package com.vietnam.lottery.business.rechargeDetail.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.rechargeDetail.request.CreateOrderRequest;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import com.vietnam.lottery.common.config.PaymentUtils;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.global.StatusEnum;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单(Order)表服务实现类
 *
 * @author Gyan
 * @since 2022-03-01 14:49:24
 */
@Service("rechargeDetail")
@Slf4j
public class RechargeDetailServiceImpl implements RechargeDetailService {
    @Autowired
    private RechargeDetailMapper rechargeDetailMapper;

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<RechargeListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return rechargeDetailMapper.list(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel pay(PayRequest request) {
        //todo:首次充值赠送20000盾
//        Long amount = 20000l;
//        if (CollectionUtils.isEmpty(rechargeDetailList)) {
//            amount += grabRedPackets.getAmount();
//        }

        //生成订单号
        String date = DateUtils.getCurrentTimeStr(DateUtils.UNSIGNED_DATETIME_PATTERN);
        String orderNo = request.getCreateBy().toString() + date;

        //支付
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setOrderId(orderNo);
        orderRequest.setAmount(request.getAmount());
        orderRequest.setType(request.getType());
        log.info("创建支付申请传参:{}", orderRequest);
        String str = PaymentUtils.createOrder(orderRequest);
        log.info("请求第三方支付返回结果:{}", str);
        JSONObject json = JSONUtil.parseObj(str);
        JSONObject data = json.getJSONObject("data");
        log.info("获取data,{}", data);
        if (!"success".equals(json.get("msg"))) {
            throw new GlobalException("Failed to create payment order");
        }
        //增加充值记录
        RechargeDetail rechargeDetail = new RechargeDetail();
        rechargeDetail.setPayType(request.getType());
        rechargeDetail.setAmount(new BigDecimal(request.getAmount()));
        rechargeDetail.setCreateBy(request.getCreateBy());
        return ResultUtil.success(rechargeDetailMapper.insert(rechargeDetail));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callBack(String body) {
        log.info("回调信息:{}", body);
        JSONObject json = JSONUtil.parseObj(body);
        JSONObject data = json.getJSONObject("data");
        Integer isPay = data.getInt("ispay");
        String orderNo = data.getStr("orderid");
        RechargeDetail recharge = rechargeDetailMapper.selectById(orderNo);
        //支付成功
        if (1 == isPay && null != recharge) {
            //更新充值记录
            recharge.setPayStatus(StatusEnum.FINISH_PAY.getCode());
            recharge.setUpdateBy(recharge.getUpdateBy());
            recharge.setUpdateDate(new Date());
            rechargeDetailMapper.insert(recharge);
        }
    }
}


