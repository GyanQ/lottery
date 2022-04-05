package com.vietnam.lottery.business.unpackRedPacketsDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.unpackRedPacketsDetail.entity.UnpackRedPacketsDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 拆红包明细(UnpackRedPacketsDetail)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-05 09:49:56
 */
@Mapper
public interface UnpackRedPacketsDetailMapper extends BaseMapper<UnpackRedPacketsDetail> {
    /* 开奖记录 */
    Page<LotteryListResponse> list(@Param("page") Page page, @Param("request") LotteryListRequest request);
}

