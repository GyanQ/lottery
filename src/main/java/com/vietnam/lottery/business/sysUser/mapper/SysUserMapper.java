package com.vietnam.lottery.business.sysUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author Gyan
 * @since 2022-01-24 18:00:05
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}

