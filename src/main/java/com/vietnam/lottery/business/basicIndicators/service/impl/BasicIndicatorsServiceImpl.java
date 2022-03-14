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
import java.time.format.DateTimeFormatter;
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
        //开始时间格式化
        DateTimeFormatter formatBegin = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        formatBegin.format(LocalDateTime.now());
        //结束时间格式化
        DateTimeFormatter formatEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");
        formatEnd.format(LocalDateTime.now());

        //当前时间
        request.setBeginDate((null != request.getBeginDate()) ? request.getBeginDate() : DateUtils.getCurrentTimeStr(DateUtils.UNSIGNED_DATE_PATTERN));

        if (null != request.getSecondBegin()) {
            //获取当前时间转换localDateTime
            LocalDateTime begin = DateUtils.parseLocalDateTime(request.getBeginDate(), DateUtils.UNSIGNED_DATE_PATTERN);
            //次留
            LocalDateTime minus = DateUtils.minus(begin, 1, ChronoUnit.DAYS);
            request.setSecond(DateUtils.localDateParseStr(minus, DateUtils.UNSIGNED_DATE_PATTERN));
            request.setSecondBegin(formatBegin.format(minus));
            request.setSecondEnd(formatEnd.format(minus));
            //3留
            LocalDateTime treeStay = DateUtils.minus(begin, 3, ChronoUnit.DAYS);
            request.setTree(DateUtils.localDateParseStr(treeStay, DateUtils.UNSIGNED_DATE_PATTERN));
            request.setTreeBegin(formatBegin.format(treeStay));
            request.setTreeEnd(formatEnd.format(treeStay));
            //7留
            LocalDateTime serverStay = DateUtils.minus(begin, 7, ChronoUnit.DAYS);
            request.setServer(DateUtils.localDateParseStr(serverStay, DateUtils.UNSIGNED_DATE_PATTERN));
            request.setServerBegin(formatBegin.format(serverStay));
            request.setServerEnd(formatEnd.format(serverStay));
            //15留
            LocalDateTime fifteenStay = DateUtils.minus(begin, 15, ChronoUnit.DAYS);
            request.setFifteen(DateUtils.localDateParseStr(fifteenStay, DateUtils.UNSIGNED_DATE_PATTERN));
            request.setFifteenBegin(formatBegin.format(fifteenStay));
            request.setFifteenEnd(formatEnd.format(fifteenStay));
            //30留
            LocalDateTime monthStay = DateUtils.minus(begin, 30, ChronoUnit.DAYS);
            request.setMoth(DateUtils.localDateParseStr(monthStay, DateUtils.UNSIGNED_DATE_PATTERN));
            request.setMothBegin(formatBegin.format(monthStay));
            request.setMothEnd(formatEnd.format(monthStay));
            return unpackRedPacketsMapper.keep(request);
        }

        //次留
        LocalDateTime minus = DateUtils.minus(LocalDateTime.now(), 1, ChronoUnit.DAYS);
        request.setSecond(DateUtils.localDateParseStr(minus, DateUtils.UNSIGNED_DATE_PATTERN));
        request.setSecondBegin(formatBegin.format(minus));
        request.setSecondEnd(formatEnd.format(minus));
        //3留
        LocalDateTime treeStay = DateUtils.minus(LocalDateTime.now(), 3, ChronoUnit.DAYS);
        request.setTree(DateUtils.localDateParseStr(treeStay, DateUtils.UNSIGNED_DATE_PATTERN));
        request.setTreeBegin(formatBegin.format(treeStay));
        request.setTreeEnd(formatEnd.format(treeStay));
        //7留
        LocalDateTime serverStay = DateUtils.minus(LocalDateTime.now(), 7, ChronoUnit.DAYS);
        request.setServer(DateUtils.localDateParseStr(serverStay, DateUtils.UNSIGNED_DATE_PATTERN));
        request.setServerBegin(formatBegin.format(serverStay));
        request.setServerEnd(formatEnd.format(serverStay));
        //15留
        LocalDateTime fifteenStay = DateUtils.minus(LocalDateTime.now(), 15, ChronoUnit.DAYS);
        request.setFifteen(DateUtils.localDateParseStr(fifteenStay, DateUtils.UNSIGNED_DATE_PATTERN));
        request.setFifteenBegin(formatBegin.format(fifteenStay));
        request.setFifteenEnd(formatEnd.format(fifteenStay));
        //30留
        LocalDateTime monthStay = DateUtils.minus(LocalDateTime.now(), 30, ChronoUnit.DAYS);
        request.setMoth(DateUtils.localDateParseStr(monthStay, DateUtils.UNSIGNED_DATE_PATTERN));
        request.setMothBegin(formatBegin.format(monthStay));
        request.setMothEnd(formatEnd.format(monthStay));
        return unpackRedPacketsMapper.keep(request);
    }
}
