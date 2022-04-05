package com.vietnam.lottery.content.back.sysUserAccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.request.UserLotteryListRequest;
import com.vietnam.lottery.business.sysUserAccount.request.WithdrawListRequest;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.response.WithdrawListResponse;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
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
    public ResultModel<Page<WithdrawListResponse>> withdrawList(WithdrawListRequest request) {
        return ResultUtil.success(sysUserAccountService.withdrawList(request));
    }
}
