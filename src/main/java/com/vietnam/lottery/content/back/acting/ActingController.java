package com.vietnam.lottery.content.back.acting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.acting.request.ActingAddRequest;
import com.vietnam.lottery.business.acting.request.ActingDeleteRequest;
import com.vietnam.lottery.business.acting.request.ActingListRequest;
import com.vietnam.lottery.business.acting.request.ActingUpdateRequest;
import com.vietnam.lottery.business.acting.response.ActingDetailResponse;
import com.vietnam.lottery.business.acting.response.ActingListResponse;
import com.vietnam.lottery.business.acting.service.ActingService;
import com.vietnam.lottery.common.utils.JwtUtil;
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
@Api(tags = "代理管理")
@RequestMapping("/acting")
public class ActingController {
    @Autowired
    private ActingService actingService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid ActingAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(actingService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel add(@RequestBody @Valid ActingUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(actingService.update(request));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<ActingListResponse>> add(@RequestBody ActingListRequest request) {
        return ResultUtil.success(actingService.list(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid ActingDeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setDeleteBy(JwtUtil.parseToken(token));
        return ResultUtil.success(actingService.delete(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("详情")
    public ResultModel<ActingDetailResponse> detail(@PathVariable("id") Long id) {
        return ResultUtil.success(actingService.detail(id));
    }
}
