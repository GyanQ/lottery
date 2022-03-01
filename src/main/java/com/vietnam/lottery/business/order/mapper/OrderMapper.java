package com.vietnam.lottery.business.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单(Order)表数据库访问层
 *
 * @author Gyan
 * @since 2022-03-01 14:49:22
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

