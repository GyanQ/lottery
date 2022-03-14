package com.vietnam.lottery.content.back.basicIndicators;

import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.request.KeepRequest;
import com.vietnam.lottery.business.basicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.KeepListResponse;
import com.vietnam.lottery.business.basicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.basicIndicators.service.BasicIndicatorsService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "基本指标")
@RequestMapping("/basic/indicators")
public class ProbabilityController {

    @Autowired
    private BasicIndicatorsService basicIndicatorsService;

    @PostMapping("/drawProbability")
    @ApiOperation("开奖概率")
    public ResultModel<List<ProbabilityResponse>> drawProbability(@RequestBody ProbabilityRequest request) {
        return ResultUtil.success(basicIndicatorsService.drawProbability(request));
    }

    @PostMapping("/statistics")
    @ApiOperation("数据统计")
    public ResultModel<IndicatorsResponse> statistics(@RequestBody IndicatorsRequest request) {
        return ResultUtil.success(basicIndicatorsService.statistics(request));
    }

    @PostMapping("/keep")
    @ApiOperation("留存")
    public ResultModel<KeepListResponse> keep(@RequestBody KeepRequest request) {
        return ResultUtil.success(basicIndicatorsService.keepList(request));
    }
}
