package com.vietnam.lottery.common.interceptor;

import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //解决错误信息乱码
        response.setCharacterEncoding("UTF-8");
        //http的header中获得token
        String token = request.getHeader(JwtUtil.getHeader());
        //token不存在
        if (null == token) throw new GlobalException("请先登录");
        Boolean flag = JwtUtil.validateToken(token);
        if (!flag) throw new GlobalException("token验证失败");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
