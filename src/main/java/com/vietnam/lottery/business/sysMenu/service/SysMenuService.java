package com.vietnam.lottery.business.sysMenu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysMenu.request.MenuAddRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuListRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuUpdateRequest;
import com.vietnam.lottery.business.sysMenu.response.MenuDetailResponse;
import com.vietnam.lottery.business.sysMenu.response.MenuLiseResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 菜单权限(SysMenu)表服务接口
 *
 * @author Gyan
 * @since 2022-02-08 10:48:11
 */
public interface SysMenuService {

    /* 新增 */
    ResultModel add(MenuAddRequest request);

    /* 列表 */
    Page<MenuLiseResponse> list(MenuListRequest request);

    /* 修改 */
    ResultModel update(MenuUpdateRequest request);

    /* 详情 */
    MenuDetailResponse detail(Long id);

    /* 删除 */
    ResultModel delete(MenuDeleteRequest request);
}

