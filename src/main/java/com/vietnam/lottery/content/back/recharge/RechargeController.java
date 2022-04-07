package com.vietnam.lottery.content.back.recharge;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.recharge.request.RechargeAddRequest;
import com.vietnam.lottery.business.recharge.request.RechargeDeleteRequest;
import com.vietnam.lottery.business.recharge.request.RechargeListRequest;
import com.vietnam.lottery.business.recharge.request.RechargeUpdateRequest;
import com.vietnam.lottery.business.recharge.response.RechargeListResponse;
import com.vietnam.lottery.business.recharge.service.RechargeService;
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
@Api(tags = "充值配置")
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    private RechargeService rechargeService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid RechargeAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(rechargeService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel add(@RequestBody @Valid RechargeUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(rechargeService.update(request));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<RechargeListResponse>> add(@RequestBody RechargeListRequest request) {
        return ResultUtil.success(rechargeService.list(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid RechargeDeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setDeleteBy(JwtUtil.parseToken(token));
        return ResultUtil.success(rechargeService.delete(request));
    }
}
