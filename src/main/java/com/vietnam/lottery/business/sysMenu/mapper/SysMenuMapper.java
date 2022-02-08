package com.vietnam.lottery.business.sysMenu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysMenu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限(SysMenu)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-08 10:48:06
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}

