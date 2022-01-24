package com.vietnam.lottery.content.back.sysUser;

import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Api(tags = "登录")
@RequestMapping("/sys")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResultModel login(HttpServletResponse httpServletResponse) {
        Map<String, Object> map = sysUserService.login();
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success();
    }
}
