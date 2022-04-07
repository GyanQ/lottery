package com.vietnam.lottery.business.recharge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.recharge.entity.Recharge;
import org.apache.ibatis.annotations.Mapper;

/**
 * 充值配置(Recharge)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-07 23:28:17
 */
@Mapper
public interface RechargeMapper extends BaseMapper<Recharge> {
}

