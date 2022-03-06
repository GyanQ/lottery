package com.vietnam.lottery.business.basicIndicators.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.basicIndicators.entity.KeepStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 留存统计(KeepStatistics)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-28 14:44:51
 */
@Mapper
public interface KeepStatisticsMapper extends BaseMapper<KeepStatistics> {

    /* 查询当前时间是否有留存记录 */
    Integer isKeep(@Param("userId") String userId);
}

