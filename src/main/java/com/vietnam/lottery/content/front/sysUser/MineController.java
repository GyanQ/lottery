package com.vietnam.lottery.content.front.sysUser;

import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUser.response.PromoteResponse;
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

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/promote")
    @ApiOperation("推广二维码")
    public ResultModel<PromoteResponse> promote(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(sysUserService.promote(Long.valueOf(userId)));
    }

    @PostMapping("/partner")
    @ApiOperation("我的伙伴")
    public ResultModel<PromoteResponse> partner(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success();
    }
}
