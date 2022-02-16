package com.vietnam.lottery.common.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //http的header中获得token
        String token = request.getHeader(JwtUtil.getHeader());

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //token不存在
        if (ObjectUtil.isEmpty(token)) throw new GlobalException("token不存在");
        //验证token
        Boolean flag = JwtUtil.validateToken(token);
        if (!flag) throw new GlobalException("token验证失败");

        response.setContentType("application/json;charset=UTF-8");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
