package com.vietnam.lottery.content.back.sysUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.request.CreateAccountRequest;
import com.vietnam.lottery.business.sysUser.request.ResetPawRequest;
import com.vietnam.lottery.business.sysUser.request.UpdatePawRequest;
import com.vietnam.lottery.business.sysUser.request.UserListRequest;
import com.vietnam.lottery.business.sysUser.response.UserDetailResponse;
import com.vietnam.lottery.business.sysUser.response.UserListResponse;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "账户管理")
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

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

    @PostMapping("/createAccount")
    @ApiOperation("创建账号")
    public ResultModel createAccount(@Valid @RequestBody CreateAccountRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        request.setCreateBy(Long.valueOf(userId));
        return ResultUtil.success(sysUserService.createAccount(request));
    }

    @PostMapping("/resetPaw")
    @ApiOperation("重置密码")
    public ResultModel resetPaw(@Valid @RequestBody ResetPawRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        request.setCreateBy(Long.valueOf(userId));
        return ResultUtil.success(sysUserService.resetPaw(request));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<UserListResponse>> list(@RequestBody UserListRequest request) {
        return ResultUtil.success(sysUserService.list(request));
    }

    @GetMapping("/detail/{account}")
    @ApiOperation("详情")
    public ResultModel<UserDetailResponse> detail(@PathVariable("account") String account) {
        return ResultUtil.success(sysUserService.detail(account));
    }
}
