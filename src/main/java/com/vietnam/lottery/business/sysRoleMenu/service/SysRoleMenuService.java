package com.vietnam.lottery.business.sysRoleMenu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vietnam.lottery.business.sysRoleMenu.entity.SysRoleMenu;
import com.vietnam.lottery.business.sysRoleMenu.request.menuConfigRequest;
import com.vietnam.lottery.business.sysRoleMenu.response.MenuPermissionsResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.List;

/**
 * 角色菜单关系表(SysRoleMenu)表服务接口
 *
 * @author Gyan
 * @since 2022-02-08 17:18:52
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /* 新增and修改 */
    ResultModel menuConfig(menuConfigRequest request);

    /* 根据角色查询菜单权限 */
    List<MenuPermissionsResponse> getByRoleMenuPermissions(Long roleId);
}

