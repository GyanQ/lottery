package com.vietnam.lottery.business.sysUser.service;

import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.response.UserGetPermissionResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.Map;

public interface SysUserService {
    /* 管理后台登录 */
    Map<String, Object> login(LoginRequest request);

    /* 注册 */
    ResultModel register(UserRegisterRequest request);

    /* 用户登录 */
    Map<String, Object> frontLogin(LoginRequest request);

    /* 用户新增 */
    ResultModel add();

    /* 修改密码*/
    ResultModel updatePaw(UpdatePawRequest request);

    /* 获取菜单权限 */
    UserGetPermissionResponse getPermission(Long id);

    /* 创建账号 */
    ResultModel createAccount(CreateAccountRequest request);

    /* 重置密码 */
    ResultModel resetPaw(ResetPawRequest request);
}
