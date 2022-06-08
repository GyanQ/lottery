package com.vietnam.lottery.business.rechargeDetail.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.entity.GrabRedPackets;
import com.vietnam.lottery.business.grabRedPackets.mapper.GrabRedPacketsMapper;
import com.vietnam.lottery.business.recharge.entity.Recharge;
import com.vietnam.lottery.business.recharge.mapper.RechargeMapper;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.rechargeDetail.request.CreateOrderRequest;
import com.vietnam.lottery.business.rechargeDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.request.SelectOrderRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.global.StatusEnum;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;
    @Resource
    private GrabRedPacketsMapper grabRedPacketsMapper;

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<RechargeListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return rechargeDetailMapper.list(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> pay(PayRequest request, String language) {
        //金额
        BigDecimal amount;
        if (null == request.getId()) {
            amount = new BigDecimal(request.getAmount() + ".0000");
        } else {
            Recharge recharge = rechargeMapper.selectById(request.getId());
            if (ObjectUtil.isEmpty(recharge)) {
                if ("0".equals(language)) {
                    throw new GlobalException("Số dư không đủ vui lòng nạp tiền");
                } else {
                    throw new GlobalException("Số dư không đủ vui lòng nạp tiền");
                }
            }
            amount = BigDecimal.ZERO.add(recharge.getAmount());
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
            if ("0".equals(language)) {
                throw new GlobalException("Số dư không đủ vui lòng nạp tiền");
            } else {
                throw new GlobalException("Số dư không đủ vui lòng nạp tiền");
            }
        }
        //增加充值记录
        RechargeDetail rechargeDetail = new RechargeDetail();
        rechargeDetail.setId(orderNo);
        rechargeDetail.setPayStatus(StatusEnum.WAIT_PAY.getCode());
        rechargeDetail.setPayType(request.getType());
        rechargeDetail.setAmount(amount);
        rechargeDetail.setCreateBy(request.getCreateBy());
        rechargeDetailMapper.insert(rechargeDetail);

        Map<String, Object> map = new HashMap<>();
        map.put("pageurl", data.getStr("pageurl"));
        map.put("ticket", data.getStr("ticket"));
        return map;
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
        log.info("充值记录recharge,{}", JSONUtil.toJsonStr(recharge));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean selectOrder(SelectOrderRequest request) {
        String body = PaymentUtils.selectOrder(request.getTicket());
        log.info("主动获取订单信息:{}", body);
        JSONObject json = JSONUtil.parseObj(body);
        JSONObject data = json.getJSONObject("data");
        log.info("获取data,{}", data);
        if (data.getInt("ispay") != 1) {
            return false;
        }
        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(grabRedPackets)) {
            return false;
        }
        SysUserAccount userAccount = new SysUserAccount();
        userAccount.setProductId(request.getId());
        userAccount.setType("2");
        userAccount.setSpending("1");
        userAccount.setAmount(grabRedPackets.getAmount());
        userAccount.setCreateBy(request.getCreateBy());
        sysUserAccountMapper.insert(userAccount);
        return true;
    }
}


