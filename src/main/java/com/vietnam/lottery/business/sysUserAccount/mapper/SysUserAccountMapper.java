package com.vietnam.lottery.business.sysUserAccount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户明细(SysUserAccount)表数据库访问层
 *
 * @author Gyan
 * @since 2022-04-05 22:30:48
 */
@Mapper
public interface SysUserAccountMapper extends BaseMapper<SysUserAccount> {
}

