package com.vietnam.lottery.content.front.sysUser;

import com.vietnam.lottery.business.sysUser.request.FrontLoginRequest;
import com.vietnam.lottery.business.sysUser.request.UserRegisterRequest;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "登录")
@RequestMapping("/sys/front")
public class UserFrontController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/frontLogin")
    @ApiOperation("登录")
    public ResultModel frontLogin(@RequestBody @Valid FrontLoginRequest request) {
        return ResultUtil.success(sysUserService.frontLogin(request));
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ResultModel register(@RequestBody @Valid UserRegisterRequest request) {
        return ResultUtil.success(sysUserService.register(request));
    }
}
