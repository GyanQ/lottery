package com.vietnam.lottery.common.config;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vietnam.lottery.business.rechargeDetail.request.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class PaymentUtils {

    public static String createOrder(CreateOrderRequest request) {
        JSONObject json = JSONUtil.createObj();
        //商户编号
        json.put("userid", "d3259916fa4344378a0fa60d9cd487ea");
        //商户订单号
        json.put("orderid", request.getOrderId());
        //商户订单类型
        String type = ("1".equals(request.getType())) ? "zalo" : "momo";
        json.put("type", type);
        //金额
        String amount = request.getAmount().toString() + ".0000";
        json.put("amount", amount);
        //订单信息通知地址
        json.put("notifyurl", "http://47.242.74.180:8090/api/web/grab/callBack");
        //前端跳转地址
        json.put("returnurl", null);
        //生成签名
        String str = ("myrxxlo5mg4j33s5wal5xse0hg3l4oli" + request.getOrderId() + amount).toLowerCase();
        //签名
        json.put("sign", md5(str));

        log.info("创建订单入参:{}", json);
        String body = HttpRequest.post("https://jsue13qsoi.77777.org/api/create").header("Content-Type", "application/json").body(json.toString()).execute().body();
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

    // 将request中的参数转换成Map
    public static Map<String, Object> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }
}