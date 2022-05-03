package com.vietnam.lottery.content.front.unpackRedPackets;

import com.vietnam.lottery.business.sysBroadcastConfig.service.SysBroadcastConfigService;
import com.vietnam.lottery.business.unpackRedPackets.response.BroadcastResponse;
import com.vietnam.lottery.business.unpackRedPackets.service.UnpackRedPacketsService;
import com.vietnam.lottery.common.utils.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "拆红包")
@RequestMapping("/web/unpack")
public class UnpackRedPacketsWebController {
    @Resource
    private UnpackRedPacketsService unpackRedPacketsService;

    @PostMapping("/lottery")
    @ApiOperation("拆红包抽奖")
    public ResultModel lottery(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(unpackRedPacketsService.lottery(userId));
    }

    @GetMapping("/broadcast")
    @ApiOperation("广播")
    public ResultModel<BroadcastResponse> broadcast(@PathVariable("flag") Boolean flag) {
        return ResultUtil.success(unpackRedPacketsService.broadcast(flag));
    }
}
