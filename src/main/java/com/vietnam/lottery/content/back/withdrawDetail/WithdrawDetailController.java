package com.vietnam.lottery.content.back.withdrawDetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawAuditRequest;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawListRequest;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawListResponse;
import com.vietnam.lottery.business.withdrawDetail.service.WithdrawDetailService;
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
@Api(tags = "提现模块")
@RequestMapping("/withdraw")
public class WithdrawDetailController {
    @Autowired
    private WithdrawDetailService withdrawDetailService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<WithdrawListResponse>> list(@RequestBody WithdrawListRequest request) {
        return ResultUtil.success(withdrawDetailService.list(request));
    }

    @PostMapping("/audit")
    @ApiOperation("提现审核")
    public ResultModel audit(@RequestBody @Valid WithdrawAuditRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(withdrawDetailService.audit(request));
    }
}
