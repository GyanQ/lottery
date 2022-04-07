package com.vietnam.lottery.business.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.customer.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客服配置(Customer)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-07 23:31:36
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}

