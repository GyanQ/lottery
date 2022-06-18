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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
            int total = unpackRedPacketsMapper.allTotal(ids, request.getBeginDate(), request.getEndDate());

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
        KeepListResponse resp = new KeepListResponse();
        //开始时间格式化
        DateTimeFormatter formatBegin = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        //结束时间格式化
        DateTimeFormatter formatEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");

        String date = DateUtils.getCurrentTimeStr(DateUtils.DATETIME_PATTERN);
        //筛选时间
        LocalDateTime local = DateUtils.parseLocalDateTime(request.getBeginDate(), DateUtils.DATETIME_PATTERN);
        //统计当天所有新增用户
        List<String> userIds = unpackRedPacketsMapper.loginTotal(formatBegin.format(local), formatEnd.format(local));
        //统计次留
        LocalDateTime minus = DateUtils.minus(local, 1, ChronoUnit.DAYS);
        List<String> secondCount = unpackRedPacketsMapper.keep(formatBegin.format(minus), formatEnd.format(minus));
        int secondSize = userIds.stream().filter(o -> secondCount.contains(o)).collect(Collectors.toList()).size();
        resp.setSecondStay(secondSize);
        int secondNum = (0 == secondSize) ? 0 : (secondSize / userIds.size()) * 100;
        resp.setSecondPer(secondNum);
        //3留
        LocalDateTime treeStay = DateUtils.minus(local, 3, ChronoUnit.DAYS);
        List<String> treeCount = unpackRedPacketsMapper.keep(formatBegin.format(treeStay), formatEnd.format(treeStay));
        int treeSize = userIds.stream().filter(o -> treeCount.contains(o)).collect(Collectors.toList()).size();
        resp.setThree(treeSize);
        int treeNum = (0 == treeSize) ? 0 : (treeSize / userIds.size()) * 100;
        resp.setThreePer(treeNum);
        //7留
        LocalDateTime serverStay = DateUtils.minus(local, 7, ChronoUnit.DAYS);
        List<String> serverCount = unpackRedPacketsMapper.keep(formatBegin.format(serverStay), formatEnd.format(serverStay));
        int serverSize = userIds.stream().filter(o -> serverCount.contains(o)).collect(Collectors.toList()).size();
        resp.setSevenStay(serverSize);
        int serverNum = (0 == serverSize) ? 0 : (serverSize / userIds.size()) * 100;
        resp.setSevenStayPer(serverNum);
        //15留
        LocalDateTime fifteenStay = DateUtils.minus(local, 15, ChronoUnit.DAYS);
        List<String> fifteenCount = unpackRedPacketsMapper.keep(formatBegin.format(fifteenStay), formatEnd.format(fifteenStay));
        int fifteenSize = userIds.stream().filter(o -> fifteenCount.contains(o)).collect(Collectors.toList()).size();
        resp.setFifteenStay(fifteenSize);
        int fifteenNum = (0 == fifteenSize) ? 0 : (fifteenSize / userIds.size()) * 100;
        resp.setFifteenStayPer(fifteenNum);
        //30留
        LocalDateTime monthStay = DateUtils.minus(local, 30, ChronoUnit.DAYS);
        List<String> monthCount = unpackRedPacketsMapper.keep(formatBegin.format(monthStay), formatEnd.format(monthStay));
        int monthSize = userIds.stream().filter(o -> monthCount.contains(o)).collect(Collectors.toList()).size();
        resp.setMonthStay(monthSize);
        int monthNum = (0 == monthSize) ? 0 : (monthSize / userIds.size()) * 100;
        resp.setMonthStayPer(monthNum);
        return resp;
    }
}
