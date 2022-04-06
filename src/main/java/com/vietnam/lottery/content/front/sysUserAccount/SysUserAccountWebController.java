package com.vietnam.lottery.content.front.sysUserAccount;

import com.vietnam.lottery.business.sysUserAccount.request.WithdrawRequest;
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
}
