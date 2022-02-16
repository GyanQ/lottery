package com.vietnam.lottery.business.grabRedPackets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.grabRedPackets.entity.GrabRedPackets;
import org.apache.ibatis.annotations.Mapper;

/**
 * 抢红包(GrabRedPackets)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 18:00:20
 */
@Mapper
public interface GrabRedPacketsMapper extends BaseMapper<GrabRedPackets> {
}

