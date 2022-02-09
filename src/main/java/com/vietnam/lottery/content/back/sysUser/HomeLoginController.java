package com.vietnam.lottery.content.back.sysUser;

import com.vietnam.lottery.business.sysUser.request.LoginRequest;
import com.vietnam.lottery.business.sysUser.request.UpdatePawRequest;
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
@RequestMapping("/sys/user")
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

    @PostMapping("/updatePaw")
    @ApiOperation("修改密码")
    public ResultModel updatePaw(@Valid @RequestBody UpdatePawRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        request.setCreateBy(Long.valueOf(userId));
        return ResultUtil.success(sysUserService.updatePaw(request));
    }
}
