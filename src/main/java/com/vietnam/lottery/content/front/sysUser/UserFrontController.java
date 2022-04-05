package com.vietnam.lottery.content.front.sysUser;

import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.IpUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Api(tags = "首页")
@RequestMapping("/sys/front")
public class UserFrontController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/amountLogin")
    @ApiOperation("账号登录")
    public ResultModel amountLogin(@RequestBody @Valid LoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        request.setIp(IpUtil.getIp(httpServletRequest));
        Map<String, Object> map = sysUserService.amountLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success(map);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ResultModel register(@RequestBody @Valid UserRegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(sysUserService.register(request));
    }

    @PostMapping("/faceBookLogin")
    @ApiOperation("faceBook登录")
    public ResultModel faceBookLogin(@RequestBody @Valid FaceBookLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        request.setIp(IpUtil.getIp(httpServletRequest));
        Map<String, Object> map = sysUserService.faceBookLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success(map);
    }

    @PostMapping("/sendSms")
    @ApiOperation("发送短信")
    public ResultModel sendSms(@RequestBody @Valid SendSmsRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(sysUserService.sendSms(request));
    }

    @PostMapping("/retrievePaw")
    @ApiOperation("找回密码")
    public ResultModel retrievePaw(@RequestBody @Valid retrievePwdRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(sysUserService.retrievePaw(request));
    }

    @PostMapping("/pawFreeLogin")
    @ApiOperation("免密码登录")
    public ResultModel pawFreeLogin(@RequestBody @Valid PawFreeLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        request.setIp(IpUtil.getIp(httpServletRequest));
        Map<String, Object> map = sysUserService.pawFreeLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success(map);
    }

    @PostMapping("/googleLogin")
    @ApiOperation("Google登录")
    public ResultModel googleLogin(@RequestBody @Valid GoogleLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        request.setIp(IpUtil.getIp(httpServletRequest));
        Map<String, Object> map = sysUserService.googleLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success(map);
    }
}
