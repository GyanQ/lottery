package com.vietnam.lottery.business.BasicIndicators.service.impl;

import com.vietnam.lottery.business.BasicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.BasicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.BasicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.BasicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.BasicIndicators.service.ProbabilityService;
import com.vietnam.lottery.business.unpackRedPackets.mapper.UnpackRedPacketsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProbabilityServiceImpl implements ProbabilityService {

    @Autowired
    private UnpackRedPacketsMapper unpackRedPacketsMapper;

    @Override
    public List<ProbabilityResponse> findProbability(ProbabilityRequest probabilityRequest) {
        return unpackRedPacketsMapper.findProbability(probabilityRequest);
    }

    @Override
    public IndicatorsResponse findIndicators(IndicatorsRequest request) {
        return unpackRedPacketsMapper.findIndicators(request);
    }
}
