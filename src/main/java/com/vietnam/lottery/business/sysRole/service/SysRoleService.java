package com.vietnam.lottery.business.sysRole.service;

import com.vietnam.lottery.business.sysRole.entity.SysRole;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 角色信息(SysRole)表服务接口
 *
 * @author Gyan
 * @since 2022-01-28 15:07:24
 */
public interface SysRoleService {

    /* 新增 */
    ResultModel add(SysRole sysRole);
}

