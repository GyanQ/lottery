package com.vietnam.lottery.business.sysRole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysRole.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息(SysRole)表数据库访问层
 *
 * @author Gyan
 * @since 2022-01-28 15:05:10
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}

