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
    Map<String, Object> amountLogin(LoginRequest request);

    /* 修改密码*/
    ResultModel updatePaw(UpdatePawRequest request);

    /* 获取菜单权限 */
    UserGetPermissionResponse getPermission(String id);

    /* 创建账号 */
    ResultModel createAccount(CreateAccountRequest request);

    /* 重置密码 */
    ResultModel resetPaw(ResetPawRequest request);

    /* 管理后台账户列表 */
    Page<UserListResponse> list(UserListRequest request);

    /* 账户管理详情 */
    UserDetailResponse detail(String id);

    /* 账号修改 */
    ResultModel update(UserDeleteRequest request);

    /* 用户列表 */
    Page<UserManageListResponse> manageList(UserManageListRequest request);

    /* 用户抢红包明细 */
    Page<GrabRedPacketsListResponse> grabRedPackets(GrabRedPacketsListRequest request);

    /* 用户详情 */
    UserDetailResponse userDetail(String id);

    /* 拉黑 */
    ResultModel pullBlack(PullBlackRequest request);

    /* facebook登录 */
    Map<String, Object> faceBookLogin(FaceBookLoginRequest request);

    /* 账户余额 */
    AccountBalanceResponse accountBalance(String userId);

    /* 发送短信 */
    ResultModel sendSms(SendSmsRequest request);

    /* 找回密码 */
    ResultModel retrievePaw(retrievePwdRequest request);

    /* 免密码登录 */
    Map<String, Object> pawFreeLogin(PawFreeLoginRequest request);

    /* google登录 */
    Map<String, Object> googleLogin(GoogleLoginRequest request);

    /* 用户角色配置 */
    ResultModel userRole(UserRoleRequest request);
}
