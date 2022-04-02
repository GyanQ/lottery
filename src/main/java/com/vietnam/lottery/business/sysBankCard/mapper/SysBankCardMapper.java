package com.vietnam.lottery.business.sysBankCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户银行信息(SysBankCard)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-01 10:49:49
 */
@Mapper
public interface SysBankCardMapper extends BaseMapper<SysBankCard> {
}

