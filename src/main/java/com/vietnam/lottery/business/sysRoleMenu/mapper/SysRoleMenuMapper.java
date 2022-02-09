package com.vietnam.lottery.business.sysRoleMenu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysRoleMenu.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关系表(SysRoleMenu)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-08 17:17:33
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}

