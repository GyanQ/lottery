package com.vietnam.lottery.business.grabRedPacketsDetail.service.impl;

import com.vietnam.lottery.business.grabRedPacketsDetail.mapper.GrabRedPacketsDetailMapper;
import com.vietnam.lottery.business.grabRedPacketsDetail.service.GrabRedPacketsDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开奖记录(LotteryDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 12:17:14
 */
@Service("grabRedPacketsDetail")
@Slf4j
public class GrabRedPacketsDetailsServiceImpl implements GrabRedPacketsDetailsService {
    @Autowired
    private GrabRedPacketsDetailMapper grabRedPacketsDetailMapper;
}

