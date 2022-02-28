package com.vietnam.lottery.business.sysSms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysSms.entity.SysSms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 短信(SysSms)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-28 15:10:18
 */
@Mapper
public interface SysSmsMapper extends BaseMapper<SysSms> {

    /* 根据手机号码查询验证码 */
    String selectByPhone(@Param("phone") String phone);
}

