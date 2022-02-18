package com.vietnam.lottery.content.back.BasicIndicators;

import com.vietnam.lottery.business.BasicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.BasicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.BasicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.BasicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.BasicIndicators.service.ProbabilityService;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "基本指标")
@RequestMapping("/basic/indicators")
public class ProbabilityController {

    @Autowired
    private ProbabilityService probabilityService;

    @PostMapping("/probabilityList")
    @ApiOperation("开奖概率")
    public ResultModel<List<ProbabilityResponse>> list(@RequestBody ProbabilityRequest request) {
        return ResultUtil.success(probabilityService.findProbability(request));
    }

    @PostMapping("/indicatorsList")
    @ApiOperation("详情")
    public ResultModel<IndicatorsResponse> detail(@RequestBody IndicatorsRequest request) {
        return ResultUtil.success(probabilityService.findIndicators(request));
    }
}
