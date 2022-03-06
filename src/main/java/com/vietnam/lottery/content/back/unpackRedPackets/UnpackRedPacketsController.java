package com.vietnam.lottery.content.back.unpackRedPackets;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackAddRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackDeleteRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackListRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackUpdateRequest;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackDetailResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackListResponse;
import com.vietnam.lottery.business.unpackRedPackets.service.UnpackRedPacketsService;
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
@Api(tags = "拆红包配置")
@RequestMapping("/unpack")
public class UnpackRedPacketsController {
    @Autowired
    private UnpackRedPacketsService unpackRedPacketsService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid UnPackAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(unpackRedPacketsService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel update(@RequestBody @Valid UnPackUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(unpackRedPacketsService.update(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid UnPackDeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setDeleteBy(JwtUtil.parseToken(token));
        return ResultUtil.success(unpackRedPacketsService.delete(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("详情")
    public ResultModel<UnPackDetailResponse> detail(@PathVariable("id") Long id) {
        return ResultUtil.success(unpackRedPacketsService.detail(id));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<UnPackListResponse>> list(@RequestBody UnPackListRequest request) {
        return ResultUtil.success(unpackRedPacketsService.list(request));
    }
}
