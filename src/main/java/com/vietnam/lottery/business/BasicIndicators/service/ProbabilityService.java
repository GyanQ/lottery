package com.vietnam.lottery.business.BasicIndicators.service;

import com.vietnam.lottery.business.BasicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.BasicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.BasicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.BasicIndicators.response.ProbabilityResponse;

import java.util.List;

public interface ProbabilityService {

    /**
     * 查看开奖几率
     * @param probabilityRequest
     * @return
     */
    List<ProbabilityResponse> findProbability(ProbabilityRequest probabilityRequest);

    /**
     * 基本设置
     * @param request
     * @return
     */
    IndicatorsResponse findIndicators(IndicatorsRequest request);
}
