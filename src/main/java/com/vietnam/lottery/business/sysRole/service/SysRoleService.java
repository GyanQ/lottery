package com.vietnam.lottery.business.sysRole.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysRole.request.RoleAddRequest;
import com.vietnam.lottery.business.sysRole.request.RoleListRequest;
import com.vietnam.lottery.business.sysRole.request.RoleUpdateRequest;
import com.vietnam.lottery.business.sysRole.response.RoleDetailResponse;
import com.vietnam.lottery.business.sysRole.response.RoleListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 角色信息(SysRole)表服务接口
 *
 * @author Gyan
 * @since 2022-01-28 15:07:24
 */
public interface SysRoleService {

    /* 新增 */
    ResultModel add(RoleAddRequest request);

    /* 列表 */
    Page<RoleListResponse> list(RoleListRequest request);

    /* 修改 */
    ResultModel update(RoleUpdateRequest request);

    /* 详情 */
    RoleDetailResponse detail(Long id);

    /* 删除 */
    ResultModel delete(MenuDeleteRequest request);
}

