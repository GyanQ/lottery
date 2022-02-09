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

    /* 添加jwt拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义排除swagger访问的路径配置
        String[] swaggerExcludes = new String[]{"/doc.html", "/swagger-resources/**", "/webjars/**", "/sys/home/**", "/sys/front/**"};

        registry.addInterceptor(authHandlerInterceptor)
                // 放开以下接口
                .addPathPatterns("/**").excludePathPatterns(swaggerExcludes);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
