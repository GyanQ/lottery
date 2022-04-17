package com.vietnam.lottery.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vietnam.lottery.business.rechargeDetail.request.CreateOrderRequest;
import com.vietnam.lottery.business.sysUserAccount.request.CreateWithdrawRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
public class PaymentUtils {

    private final static String userid = "d3259916fa4344378a0fa60d9cd487ea";

    private final static String token = "myrxxlo5mg4j33s5wal5xse0hg3l4oli";

    public static String createOrder(CreateOrderRequest request) {
        JSONObject json = JSONUtil.createObj();
        //商户编号
        json.put("userid", userid);
        //商户订单号
        json.put("orderid", request.getOrderId());
        //商户订单类型
        String type = ("1".equals(request.getType())) ? "zalo" : "momo";
        json.put("type", type);
        //金额
        String amount = request.getAmount().toString();
        json.put("amount", amount);
        //订单信息通知地址
        json.put("notifyurl", "https://wwww.redpz.com/api/web/recharge/callBack");
        //前端跳转地址
        json.put("returnurl", "https://www.redpz.com/#/grab");
        //生成签名
        String str = (token + request.getOrderId() + amount).toLowerCase();
        //签名
        json.put("sign", md5(str));

        log.info("创建订单入参:{}", json);
        String body = HttpRequest.post("https://jsue13qsoi.77777.org/api/create").header("Content-Type", "application/json").body(json.toString()).execute().body();
        return body;
    }

    public static String createWithdraw(CreateWithdrawRequest request) {
        JSONObject json = JSONUtil.createObj();
        //商户编号
        json.put("userid", userid);
        //商户订单号
        json.put("orderid", request.getOrderNo());
        //金额
        String amount = request.getAmount().toString();
        json.put("amount", amount);
        //订单信息通知地址
        json.put("notifyurl", "https://admin.redpz.com/api/sys/account/callBack");
        //生成签名
        String str = (token + request.getOrderNo() + amount).toLowerCase();
        //签名
        json.put("sign", md5(str));
        //订单其他数据(提现必传)
        json.put("payload", request.getPayload());

        log.info("创建订单入参:{}", json);
        String body = HttpRequest.post("https://jsue13qsoi.77777.org/api/wd").header("Content-Type", "application/json").body(json.toString()).execute().body();
        return body;
    }

    /* 将字符串MD5加码 生成32位md5码*/
    private static String md5(String str) {
        try {
            return DigestUtils.md5Hex(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }
}