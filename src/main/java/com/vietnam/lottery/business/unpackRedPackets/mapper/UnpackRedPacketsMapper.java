package com.vietnam.lottery.business.unpackRedPackets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.unpackRedPackets.entity.UnpackRedPackets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拆红包(UnpackRedPackets)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 18:00:36
 */
@Mapper
public interface UnpackRedPacketsMapper extends BaseMapper<UnpackRedPackets> {

    /* 开奖概率 */
    List<ProbabilityResponse> drawProbability(@Param("request") ProbabilityRequest request);

    /* 数据统计 */
    IndicatorsResponse statistics(@Param("request") IndicatorsRequest request);
}

