package com.vietnam.lottery.business.unpackRedPackets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.BasicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.BasicIndicators.request.ProbabilityRequest;
import com.vietnam.lottery.business.BasicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.BasicIndicators.response.ProbabilityResponse;
import com.vietnam.lottery.business.unpackRedPackets.entity.UnpackRedPackets;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 拆红包(UnpackRedPackets)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-16 18:00:36
 */
@Mapper
public interface UnpackRedPacketsMapper extends BaseMapper<UnpackRedPackets> {

    /**
     * 查看开奖概率列表
     * @param probabilityRequest
     * @return
     */
    List<ProbabilityResponse> findProbability(ProbabilityRequest probabilityRequest);

    IndicatorsResponse findIndicators(IndicatorsRequest indicatorsRequest);
}

