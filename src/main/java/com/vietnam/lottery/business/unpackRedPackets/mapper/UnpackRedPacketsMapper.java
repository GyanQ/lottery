package com.vietnam.lottery.business.unpackRedPackets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.basicIndicators.request.IndicatorsRequest;
import com.vietnam.lottery.business.basicIndicators.response.GrabResponse;
import com.vietnam.lottery.business.basicIndicators.response.IndicatorsResponse;
import com.vietnam.lottery.business.basicIndicators.response.UnpackResponse;
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

    /* 数据统计 */
    IndicatorsResponse statistics(@Param("request") IndicatorsRequest request);

    List<String> keep(@Param("begin") String begin, @Param("end") String end);

    List<String> loginTotal(@Param("begin") String begin, @Param("end") String end);

    /* 查询所有抢红包*/
    List<GrabResponse> selectGrab();

    //根据抢红包id查询拆红包
    List<UnpackResponse> selectUnpackById(@Param("id") String id, @Param("begin") String begin, @Param("end") String end, @Param("total") int total);

    List<String> ids(@Param("id") String id);

    int allTotal(@Param("ids") List<String> ids, @Param("begin") String begin, @Param("end") String end);
}

