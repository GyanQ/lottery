package com.vietnam.lottery.content.back.sysUserAccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.request.*;
import com.vietnam.lottery.business.sysUserAccount.response.CommissionListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.SubordinateListListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
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
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "用户账户")
@RequestMapping("/sys/account")
public class SysUserAccountController {
    @Autowired
    private SysUserAccountService sysUserAccountService;

    @PostMapping("/lotteryList")
    @ApiOperation("开奖记录")
    public ResultModel<Page<UserLotteryListResponse>> lotteryList(@RequestBody UserLotteryListRequest request, BindingResult bindingResult) {
        return ResultUtil.success(sysUserAccountService.lotteryList(request));
    }

    @PostMapping("/withdrawList")
    @ApiOperation("提现列表")
    public ResultModel<Page<WithdrawListResponse>> withdrawList(@RequestBody WithdrawListRequest request) {
        return ResultUtil.success(sysUserAccountService.withdrawList(request));
    }

    @PostMapping("/commissionsList")
    @ApiOperation("代理列表")
    public ResultModel<Page<CommissionListResponse>> commissionsList(@RequestBody CommissionListRequest request) {
        return ResultUtil.success(sysUserAccountService.commissionsList(request));
    }

    @PostMapping("/subordinateList")
    @ApiOperation("下级列表")
    public ResultModel<List<SubordinateListListResponse>> withdrawList(@RequestBody @Valid SubordinateListListRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUserId(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserAccountService.subordinateList(request));
    }

    @PostMapping("/withdrawAudit")
    @ApiOperation("提现审核")
    public ResultModel withdrawAudit(@RequestBody @Valid WithdrawAuditRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserAccountService.withdrawAudit(request));
    }
}
