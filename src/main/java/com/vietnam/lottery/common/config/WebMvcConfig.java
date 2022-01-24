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
        registry.addInterceptor(authHandlerInterceptor)
                // 放开以下接口
                .addPathPatterns("/**").excludePathPatterns("/doc.html").excludePathPatterns("/swagger-resources/**").excludePathPatterns("/sys/login");
    }
}
