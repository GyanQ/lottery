package com.vietnam.lottery.business.basicIndicators.service.impl;

import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.request.KeepRequest;
import com.vietnam.lottery.business.basicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.basicIndicators.response.GrabResponse;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.KeepListResponse;
import com.vietnam.lottery.business.basicIndicators.service.BasicIndicatorsService;
import com.vietnam.lottery.business.unpackRedPackets.mapper.UnpackRedPacketsMapper;
import com.vietnam.lottery.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BasicIndicatorsServiceImpl implements BasicIndicatorsService {

    @Autowired
    private UnpackRedPacketsMapper unpackRedPacketsMapper;

    @Override
    public List<GrabResponse> drawProbability(ProbabilityRequest request) {
        List<GrabResponse> resp = unpackRedPacketsMapper.selectGrab();
        if (CollectionUtils.isEmpty(resp)) return resp;

        for (GrabResponse o : resp) {
            List<String> ids = unpackRedPacketsMapper.ids(o.getId());
            if (CollectionUtils.isEmpty(ids)) continue;
            int total = unpackRedPacketsMapper.allTotal(ids);

            o.setUnpackList(unpackRedPacketsMapper.selectUnpackById(o.getId(), request.getBeginDate(), request.getEndDate(), total));

        }
        return resp;
    }

    @Override
    public IndicatorsResponse statistics(IndicatorsRequest request) {
        return unpackRedPacketsMapper.statistics(request);
    }

    @Override
    public KeepListResponse keepList(KeepRequest request) {
        //开始时间格式化
        DateTimeFormatter formatBegin = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        //结束时间格式化
        DateTimeFormatter formatEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");

        //筛选时间
        LocalDateTime local = (!StringUtils.isBlank(request.getBeginDate())) ? DateUtils.parseLocalDateTime(request.getBeginDate(), DateUtils.UNSIGNED_DATE_PATTERN) : LocalDateTime.now();

        //次留
        LocalDateTime minus = DateUtils.minus(local, 1, ChronoUnit.DAYS);
        request.setSecondBegin(formatBegin.format(minus));
        request.setSecondEnd(formatEnd.format(minus));
        //3留
        LocalDateTime treeStay = DateUtils.minus(local, 3, ChronoUnit.DAYS);
        request.setTreeBegin(formatBegin.format(treeStay));
        request.setTreeEnd(formatEnd.format(treeStay));
        //7留
        LocalDateTime serverStay = DateUtils.minus(local, 7, ChronoUnit.DAYS);
        request.setServerBegin(formatBegin.format(serverStay));
        request.setServerEnd(formatEnd.format(serverStay));
        //15留
        LocalDateTime fifteenStay = DateUtils.minus(local, 15, ChronoUnit.DAYS);
        request.setFifteenBegin(formatBegin.format(fifteenStay));
        request.setFifteenEnd(formatEnd.format(fifteenStay));
        //30留
        LocalDateTime monthStay = DateUtils.minus(local, 30, ChronoUnit.DAYS);
        request.setMothBegin(formatBegin.format(monthStay));
        request.setMothEnd(formatEnd.format(monthStay));
        return unpackRedPacketsMapper.keep(request);
    }
}
