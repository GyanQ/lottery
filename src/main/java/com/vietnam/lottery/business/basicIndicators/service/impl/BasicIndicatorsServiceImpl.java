package com.vietnam.lottery.business.basicIndicators.service.impl;

import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.request.KeepRequest;
import com.vietnam.lottery.business.basicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.KeepListResponse;
import com.vietnam.lottery.business.basicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.basicIndicators.service.BasicIndicatorsService;
import com.vietnam.lottery.business.unpackRedPackets.mapper.UnpackRedPacketsMapper;
import com.vietnam.lottery.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BasicIndicatorsServiceImpl implements BasicIndicatorsService {

    @Autowired
    private UnpackRedPacketsMapper unpackRedPacketsMapper;

    @Override
    public List<ProbabilityResponse> drawProbability(ProbabilityRequest request) {
        return unpackRedPacketsMapper.drawProbability(request);
    }

    @Override
    public IndicatorsResponse statistics(IndicatorsRequest request) {
        return unpackRedPacketsMapper.statistics(request);
    }

    @Override
    public KeepListResponse keepList(KeepRequest request) {
        //次留
        LocalDateTime secondStay = DateUtils.minus(LocalDateTime.now(), 1, ChronoUnit.DAYS);
        String second = DateUtils.localDateParseStr(secondStay, DateUtils.DATE_PATTERN);
        //3留
        LocalDateTime treeStay = DateUtils.minus(LocalDateTime.now(), 3, ChronoUnit.DAYS);
        String tree = DateUtils.localDateParseStr(treeStay, DateUtils.DATE_PATTERN);
        //7留
        LocalDateTime serverStay = DateUtils.minus(LocalDateTime.now(), 7, ChronoUnit.DAYS);
        String server = DateUtils.localDateParseStr(serverStay, DateUtils.DATE_PATTERN);
        //15留
        LocalDateTime fifteenStay = DateUtils.minus(LocalDateTime.now(), 15, ChronoUnit.DAYS);
        String fifteen = DateUtils.localDateParseStr(fifteenStay, DateUtils.DATE_PATTERN);
        //30留
        LocalDateTime monthStay = DateUtils.minus(LocalDateTime.now(), 30, ChronoUnit.DAYS);
        String moth = DateUtils.localDateParseStr(monthStay, DateUtils.DATE_PATTERN);
        return null;
    }
}
