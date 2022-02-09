package com.vietnam.lottery.business.sysRoleMenu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vietnam.lottery.business.sysRoleMenu.entity.SysRoleMenu;
import com.vietnam.lottery.business.sysRoleMenu.mapper.SysRoleMenuMapper;
import com.vietnam.lottery.business.sysRoleMenu.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系表(SysRoleMenu)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-08 17:18:53
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
}

