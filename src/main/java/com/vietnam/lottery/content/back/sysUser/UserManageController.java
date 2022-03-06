package com.vietnam.lottery.content.back.sysUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.request.GrabRedPacketsListRequest;
import com.vietnam.lottery.business.sysUser.request.PullBlackRequest;
import com.vietnam.lottery.business.sysUser.request.UserManageListRequest;
import com.vietnam.lottery.business.sysUser.response.GrabRedPacketsListResponse;
import com.vietnam.lottery.business.sysUser.response.UserDetailResponse;
import com.vietnam.lottery.business.sysUser.response.UserManageListResponse;
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
@Api(tags = "用户管理")
@RequestMapping("/sys/userManage")
public class UserManageController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/list")
    @ApiOperation("用户列表")
    public ResultModel<Page<UserManageListResponse>> list(@RequestBody UserManageListRequest request) {
        return ResultUtil.success(sysUserService.manageList(request));
    }

    @PostMapping("/redPacketsList")
    @ApiOperation("用户抢红包明细")
    public ResultModel<Page<GrabRedPacketsListResponse>> redPacketsList(@RequestBody @Valid GrabRedPacketsListRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(sysUserService.grabRedPackets(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("用户详情")
    public ResultModel<UserDetailResponse> detail(@PathVariable("id") String id) {
        return ResultUtil.success(sysUserService.userDetail(id));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel update(@RequestBody @Valid PullBlackRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserService.pullBlack(request));
    }
}
