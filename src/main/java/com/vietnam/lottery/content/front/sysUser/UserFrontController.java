package com.vietnam.lottery.content.front.sysUser;

import com.vietnam.lottery.business.sysUser.request.LoginRequest;
import com.vietnam.lottery.business.sysUser.request.UserRegisterRequest;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Api(tags = "首页")
@RequestMapping("/sys/front")
public class UserFrontController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/frontLogin")
    @ApiOperation("登录")
    public ResultModel frontLogin(@RequestBody @Valid LoginRequest request, HttpServletResponse httpServletResponse) {
        Map<String, Object> map = sysUserService.frontLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success("登录成功！");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ResultModel register(@RequestBody @Valid UserRegisterRequest request) {
        return ResultUtil.success(sysUserService.register(request));
    }
}
