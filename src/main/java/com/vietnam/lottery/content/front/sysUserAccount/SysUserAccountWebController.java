package com.vietnam.lottery.content.front.sysUserAccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUserAccount.request.CommissionLDetailRequest;
import com.vietnam.lottery.business.sysUserAccount.request.SubordinateListListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawDetailRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawRequest;
import com.vietnam.lottery.business.sysUserAccount.response.CommissionLDetailResponse;
import com.vietnam.lottery.business.sysUserAccount.response.SubordinateListListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawDetailResponse;
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
@RequestMapping("/web/user/account")
public class SysUserAccountWebController {
    @Autowired
    private SysUserAccountService sysUserAccountService;

    @PostMapping("/withdraw")
    @ApiOperation("提现")
    public ResultModel withdraw(@RequestBody @Valid WithdrawRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserAccountService.withdraw(request));
    }

    @PostMapping("/accountBalance")
    @ApiOperation("账户余额")
    public ResultModel<AccountBalanceResponse> accountBalance(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(sysUserAccountService.accountBalance(userId));
    }

    @PostMapping("/partner")
    @ApiOperation("我的伙伴")
    public ResultModel<List<SubordinateListListResponse>> partner(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        SubordinateListListRequest listRequest = new SubordinateListListRequest();
        listRequest.setUserId(userId);
        return ResultUtil.success(sysUserAccountService.subordinateList(listRequest));
    }

    @PostMapping("/commissionDetails")
    @ApiOperation("分佣明细")
    public ResultModel<Page<CommissionLDetailResponse>> commissionDetails(@RequestBody CommissionLDetailRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUserId(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserAccountService.commissionDetails(request));
    }

    @PostMapping("/withdrawDetail")
    @ApiOperation("提现记录")
    public ResultModel<Page<WithdrawDetailResponse>> withdrawDetail(@RequestBody WithdrawDetailRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserAccountService.withdrawDetail(request));
    }
}
