package com.vietnam.lottery.business.lotteryDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.lotteryDetail.entity.LotteryDetail;
import com.vietnam.lottery.business.lotteryDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.lotteryDetail.response.LotteryListResponse;
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
public interface LotteryDetailMapper extends BaseMapper<LotteryDetail> {
    /* 开奖记录 */
    Page<LotteryListResponse> list(@Param("page") Page page, @Param("request") LotteryListRequest request);

    /* 拆红包余额*/
    LotteryAmountResponse lotteryAmount(@Param("userId") Long userId);
}

