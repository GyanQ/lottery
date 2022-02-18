package com.vietnam.lottery.business.sysUser.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.response.*;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.Map;

public interface SysUserService {
    /* 管理后台登录 */
    Map<String, Object> login(LoginRequest request);

    /* 注册 */
    ResultModel register(UserRegisterRequest request);

    /* 用户登录 */
    Map<String, Object> frontLogin(LoginRequest request);

    /* 修改密码*/
    ResultModel updatePaw(UpdatePawRequest request);

    /* 获取菜单权限 */
    UserGetPermissionResponse getPermission(Long id);

    /* 创建账号 */
    ResultModel createAccount(CreateAccountRequest request);

    /* 重置密码 */
    ResultModel resetPaw(ResetPawRequest request);

    /* 管理后台账户列表 */
    Page<UserListResponse> list(UserListRequest request);

    /* 账户管理详情 */
    UserDetailResponse detail(Long id);

    /* 账号修改 */
    ResultModel update(UserDeleteRequest request);

    /* 用户列表 */
    Page<UserManageListResponse> manageList(UserManageListRequest request);

    /* 用户抢红包明细 */
    Page<GrabRedPacketsListResponse> grabRedPackets(GrabRedPacketsListRequest request);
}
