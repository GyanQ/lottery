package com.vietnam.lottery.content.front.grabRedPackets;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.request.BetRequest;
import com.vietnam.lottery.business.grabRedPackets.request.ListRequest;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.business.grabRedPackets.service.GrabRedPacketsService;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@Api(tags = "抢红包")
@RequestMapping("/web/grab")
@Slf4j
public class GrabRedPacketsWebController {
    @Autowired
    private GrabRedPacketsService grabRedPacketsService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<ListResponse>> list(@RequestBody ListRequest request) {
        return ResultUtil.success(grabRedPacketsService.list(request));
    }

    @PostMapping("/bet")
    @ApiOperation("下注")
    public ResultModel bet(@RequestBody @Valid BetRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(grabRedPacketsService.bet(request));
    }

    @PostMapping("/callBack")
    @ApiOperation("支付回调")
    public String callBack(HttpServletRequest httpServletRequest) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String body = sb.toString();
            log.info(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
