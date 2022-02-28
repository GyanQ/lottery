package com.vietnam.lottery.common.config;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vietnam.lottery.business.order.request.CreateOrderRequest;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PaymentUtils {

    public static JSONObject createOrder(CreateOrderRequest request) {
        Map<String, Object> map = new HashMap<>();
        //token
        String token = "abe3eb22-4263-4363-88d1-7975a83edd4f";
        //商户编号
        map.put("userid", "wy070");
        //商户订单号
        map.put("orderid", request.getOrderId());
        //商户订单类型
        map.put("type", request.getType());
        //金额
        map.put("amount", request.getAmount());
        //订单信息通知地址
        map.put("notifyurl", null);
        //前端跳转地址
        map.put("returnurl", null);
        //生成签名
        String str = (token + request.getOrderId() + request.getAmount().toString()).toLowerCase();
        //签名
        map.put("sign", md5(str));

        JSONObject jsonObject = null;
        return JSONUtil.parseObj(HttpUtil.post("https://api.zf77777.org/api/create", map));
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