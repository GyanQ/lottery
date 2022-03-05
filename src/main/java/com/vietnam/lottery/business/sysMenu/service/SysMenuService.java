package com.vietnam.lottery.business.sysMenu.service;

import com.vietnam.lottery.business.sysMenu.request.MenuAddRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuUpdateRequest;
import com.vietnam.lottery.business.sysMenu.response.MenuDetailResponse;
import com.vietnam.lottery.business.sysMenu.response.MenuLiseResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.List;

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
    List<MenuLiseResponse> list();

    /* 修改 */
    ResultModel update(MenuUpdateRequest request);

    /* 详情 */
    MenuDetailResponse detail(Long id);

    /* 删除 */
    ResultModel delete(MenuDeleteRequest request);
}

