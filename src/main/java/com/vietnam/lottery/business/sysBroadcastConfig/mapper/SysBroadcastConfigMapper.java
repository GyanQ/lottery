package com.vietnam.lottery.business.sysBroadcastConfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysBroadcastConfig.entity.SysBroadcastConfig;
import com.vietnam.lottery.business.unpackRedPackets.response.BroadcastResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 广播配置(SysBroadcastConfig)表数据库访问层
 *
 * @author Guam
 * @since 2022-05-03 22:16:01
 */
@Mapper
public interface SysBroadcastConfigMapper extends BaseMapper<SysBroadcastConfig> {
    //查询广播
    BroadcastResponse broadcast();

    //查询全部广播
    List<BroadcastResponse> selectRandom();
}

