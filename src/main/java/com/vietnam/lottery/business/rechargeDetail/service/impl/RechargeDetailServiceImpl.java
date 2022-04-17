package com.vietnam.lottery.business.rechargeDetail.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.recharge.entity.Recharge;
import com.vietnam.lottery.business.recharge.mapper.RechargeMapper;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.rechargeDetail.request.CreateOrderRequest;
import com.vietnam.lottery.business.rechargeDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.global.StatusEnum;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Resource
    private RechargeMapper rechargeMapper;

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<RechargeListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return rechargeDetailMapper.list(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String pay(PayRequest request) {
        //金额
        BigDecimal amount = BigDecimal.ZERO;
        if (StringUtils.isBlank(request.getId())) {
            amount = amount.add(request.getAmount());
        } else {
            Recharge recharge = rechargeMapper.selectById(request.getId());
            if (ObjectUtil.isEmpty(recharge)) {
                throw new GlobalException("Không thể nhận được số tiền, nạp tiền không thành công");
            }
            amount = amount.add(recharge.getAmount());
        }

        //生成订单号
        String date = DateUtils.getCurrentTimeStr(DateUtils.UNSIGNED_DATETIME_PATTERN);
        String orderNo = request.getCreateBy().toString() + date;

        //支付
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setOrderId(orderNo);
        orderRequest.setAmount(amount);
        orderRequest.setType(request.getType());
        log.info("创建支付申请传参:{}", orderRequest);
        String str = PaymentUtils.createOrder(orderRequest);
        log.info("请求第三方支付返回结果:{}", str);
        JSONObject json = JSONUtil.parseObj(str);
        JSONObject data = json.getJSONObject("data");
        log.info("获取data,{}", data);
        if (json.getInt("code") != 1) {
            throw new GlobalException("Nạp tiền không thành công");
        }
        //增加充值记录
        RechargeDetail rechargeDetail = new RechargeDetail();
        rechargeDetail.setId(orderNo);
        rechargeDetail.setPayStatus(StatusEnum.WAIT_PAY.getCode());
        rechargeDetail.setPayType(request.getType());
        rechargeDetail.setAmount(amount);
        rechargeDetail.setCreateBy(request.getCreateBy());
        rechargeDetailMapper.insert(rechargeDetail);
        return data.getStr("pageurl");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callBack(String body) {
        log.info("充值回调信息body:{}", body);
        JSONObject json = JSONUtil.parseObj(body);
        JSONObject data = json.getJSONObject("data");
        log.info("获取充值回调信息data:{}", data);
        Integer isPay = data.getInt("ispay");
        log.info("获取充值回调信息ispay:{}", isPay);
        String orderNo = data.getStr("orderid");
        log.info("获取充值回调信息orderNo:{}", orderNo);
        RechargeDetail recharge = rechargeDetailMapper.selectById(orderNo);
        log.info("充值记录recharge,{}", recharge);
        if (ObjectUtil.isEmpty(recharge)) return;

        if (isPay != 1) {
            log.info("充值失败");
            return;
        }
        //更新充值记录
        recharge.setPayStatus(StatusEnum.FINISH_PAY.getCode());
        recharge.setUpdateBy(recharge.getCreateBy());
        recharge.setUpdateDate(new Date());
        rechargeDetailMapper.updateById(recharge);
    }
}


