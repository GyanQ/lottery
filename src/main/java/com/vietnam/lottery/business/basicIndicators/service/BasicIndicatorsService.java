package com.vietnam.lottery.business.basicIndicators.service;

import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.request.KeepRequest;
import com.vietnam.lottery.business.basicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.KeepListResponse;
import com.vietnam.lottery.business.basicIndicators.response.ProbabilityResponse;

import java.util.List;

public interface BasicIndicatorsService {

    /* 开奖概率 */
    List<ProbabilityResponse> drawProbability(ProbabilityRequest request);

    /* 数据统计 */
    IndicatorsResponse statistics(IndicatorsRequest request);

    /* 用户留存列表 */
    KeepListResponse keepList(KeepRequest request);
}
