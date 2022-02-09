package com.vietnam.lottery.content.back.sysUser;

import com.vietnam.lottery.business.sysUser.request.LoginRequest;
import com.vietnam.lottery.business.sysUser.response.UserGetPermissionResponse;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
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
@RequestMapping("/sys/home")
public class HomeLoginController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResultModel login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String, Object> map = sysUserService.login(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success("登录成功！");
    }

    @PostMapping("/getPermission")
    @ApiOperation("获取权限")
    public ResultModel<UserGetPermissionResponse> getPermission(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(sysUserService.getPermission(Long.valueOf(userId)));
    }
}
