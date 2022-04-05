package com.vietnam.lottery.business.grabRedPacketsDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.grabRedPacketsDetail.entity.GrabRedPacketsDetail;
import com.vietnam.lottery.business.sysUser.response.LotteryAmountResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 开奖记录(LotteryDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-17 12:17:12
 */
@Mapper
public interface GrabRedPacketsDetailMapper extends BaseMapper<GrabRedPacketsDetail> {
    /* 拆红包余额*/
    LotteryAmountResponse lotteryAmount(@Param("userId") String userId);
}

