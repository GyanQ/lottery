package com.vietnam.lottery.business.unpackRedPackets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.unpackRedPackets.entity.UnpackRedPackets;
import org.apache.ibatis.annotations.Mapper;

/**
 * 拆红包(UnpackRedPackets)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 18:00:36
 */
@Mapper
public interface UnpackRedPacketsMapper extends BaseMapper<UnpackRedPackets> {
}

