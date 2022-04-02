package com.vietnam.lottery.content.front.sysBankCard;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.request.BankCardAddRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardDeleteRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardUpdateRequest;
import com.vietnam.lottery.business.sysBankCard.response.BankCardListResponse;
import com.vietnam.lottery.business.sysBankCard.service.SysBankCardService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "银行卡")
@RequestMapping("/web/back/crad")
@Slf4j
public class SysBankCardWebController {
    @Autowired
    private SysBankCardService sysBankCardService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid BankCardAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysBankCardService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel add(@RequestBody @Valid BankCardUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysBankCardService.update(request));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<BankCardListResponse>> add(@RequestBody BankCardListRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUserId(JwtUtil.parseToken(token));
        return ResultUtil.success(sysBankCardService.list(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid BankCardDeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUserId(JwtUtil.parseToken(token));
        return ResultUtil.success(sysBankCardService.delete(request));
    }
}
