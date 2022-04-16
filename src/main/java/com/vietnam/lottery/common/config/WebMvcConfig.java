package com.vietnam.lottery.common.config;

import com.vietnam.lottery.common.interceptor.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthHandlerInterceptor authHandlerInterceptor;

    /* 添加拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义排除swagger访问的路径配置
        String[] swaggerExcludes = new String[]{"/doc.html", "/swagger-resources/**", "/webjars/**", "/sys/home/**", "/sys/front/**", "/error", "/web/recharge/callBack", "/sys/account/callBack"};

        //拦截器规则
        registry.addInterceptor(authHandlerInterceptor)
                //拦截所有接口
                .addPathPatterns("/**")
                //放开以下接口
                .excludePathPatterns(swaggerExcludes);
    }
}
