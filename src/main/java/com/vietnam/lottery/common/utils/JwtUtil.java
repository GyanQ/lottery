package com.vietnam.lottery.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    /* token头部 */
    @Value("${jwt.header}")
    private static String header;

    public void setHeader(String header) {
        this.header = header;
    }

    public static String getHeader() {
        return header;
    }

    /* token前缀 */
    @Value("${jwt.tokenPrefix}")
    private static String tokenPrefix;

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    /* token密钥 */
    @Value("${jwt.secret}")
    private static String secret;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /*  token过期时间 */
    @Value("${jwt.expireTime}")
    private static Long expireTime;

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    // 创建token
    public static String createToken(Map<String, Object> map) {
        Date date = new Date(System.currentTimeMillis() + expireTime * 60 * 1000);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v.toString());
        });
        builder.withExpiresAt(date);
        return builder.sign(algorithm);
    }

    // 验证token
    public static Boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (Exception e) {
            throw new ApiException("The token is invalid, please get it again");
        }
        return true;
    }

    // 解析token 获取userId
    public static String parseToken(String token) {
        try {
            String userId = JWT.decode(token).getClaim("userId").asString();
            return userId;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解析token 获取语言 改动太大 懒得改!
    public static String parseLanguage(String token) {
        try {
            String language = JWT.decode(token).getClaim("language").asString();
            return language;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
