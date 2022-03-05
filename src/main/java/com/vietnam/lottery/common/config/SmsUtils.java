package com.vietnam.lottery.common.config;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsUtils {
    /**
     * 发送短信
     *
     * @param to   手机号码
     * @param code 验证码
     */
    public static void send(String to, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hongkong", "LTAI5tM9Efn4ZXdSCFX7vxq7", "ar1W6uqk3hAgJKdZnkt2S3aUh9yFVH");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.ap-southeast-1.aliyuncs.com");
        request.setSysVersion("2018-05-01");
        request.setSysAction("SendMessageToGlobe");
        request.putQueryParameter("RegionId", "cn-hongkong");
        request.putQueryParameter("To", to);
        request.putQueryParameter("Message", "[ Lì xì may mắn ] mã xác nhận:" + code);
        try {
            client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    /* 生成6位随机数验证码 */
    public static String code() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code = code + (int) (Math.random() * 9);
        }
        return code;
    }
}