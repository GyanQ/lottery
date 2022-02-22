package com.vietnam.lottery.content.front.sysUser;

import com.vietnam.lottery.business.sysUser.request.FaceBookLoginRequest;
import com.vietnam.lottery.business.sysUser.request.UserRegisterRequest;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
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
@Api(tags = "我的")
@RequestMapping("/sys/mine")
public class MineController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/accountBalance")
    @ApiOperation("账户余额")
    public ResultModel<AccountBalanceResponse> accountBalance(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(sysUserService.accountBalance(Long.valueOf(userId)));
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
    public ResultModel faceBookLogin(@RequestBody @Valid FaceBookLoginRequest request, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String, Object> map = sysUserService.faceBookLogin(request);
        httpServletResponse.setHeader(JwtUtil.getHeader(), map.get("token").toString());
        return ResultUtil.success("登录成功！");
    }
}
