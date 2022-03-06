package com.vietnam.lottery.business.sysLoginDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysLoginDetail.entity.SysLoginDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户登录详情(SysLoginDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-18 11:50:25
 */
@Mapper
public interface SysLoginDetailMapper extends BaseMapper<SysLoginDetail> {
    /* 获取用户最后一次登录时间 */
    String selectDate(@Param("id") String id);
}

