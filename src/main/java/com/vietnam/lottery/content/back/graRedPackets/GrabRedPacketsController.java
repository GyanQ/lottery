package com.vietnam.lottery.content.back.graRedPackets;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.request.AddRequest;
import com.vietnam.lottery.business.grabRedPackets.request.DeleteRequest;
import com.vietnam.lottery.business.grabRedPackets.request.ListRequest;
import com.vietnam.lottery.business.grabRedPackets.request.UpdateRequest;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.business.grabRedPackets.service.GrabRedPacketsService;
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
@Api(tags = "抢红包配置")
@RequestMapping("/grab")
public class GrabRedPacketsController {
    @Autowired
    private GrabRedPacketsService grabRedPacketsService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid AddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(grabRedPacketsService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel update(@RequestBody @Valid UpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(grabRedPacketsService.update(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid DeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setDeleteBy(JwtUtil.parseToken(token));
        return ResultUtil.success(grabRedPacketsService.delete(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("详情")
    public ResultModel<DetailResponse> detail(@PathVariable("id") Long id) {
        return ResultUtil.success(grabRedPacketsService.detail(id));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<ListResponse>> list(@RequestBody ListRequest request) {
        return ResultUtil.success(grabRedPacketsService.list(request));
    }
}
