package com.vietnam.lottery.content.front.rechargeDetail;

import com.vietnam.lottery.business.rechargeDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.GlobalException;
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
@Api(tags = "充值")
@RequestMapping("/web/recharge")
@Slf4j
public class RechargeDetailWebController {
    @Autowired
    private RechargeDetailService rechargeDetailService;

    @PostMapping("/pay")
    @ApiOperation("充值")
    public ResultModel pay(@RequestBody @Valid PayRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(rechargeDetailService.pay(request));
    }

    @PostMapping("/callBack")
    @ApiOperation("支付回调")
    public String callBack(HttpServletRequest httpServletRequest) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String body = sb.toString();
            rechargeDetailService.callBack(body);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return "success";
    }
}
