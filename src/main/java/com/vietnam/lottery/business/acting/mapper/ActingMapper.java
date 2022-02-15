package com.vietnam.lottery.business.acting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.acting.entity.Acting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理配置(Acting)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-15 16:24:11
 */
@Mapper
public interface ActingMapper extends BaseMapper<Acting> {
}

