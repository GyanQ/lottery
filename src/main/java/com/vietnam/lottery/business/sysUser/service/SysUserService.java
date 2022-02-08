package com.vietnam.lottery.business.sysUser.service;

import com.vietnam.lottery.business.sysUser.request.LoginRequest;
import com.vietnam.lottery.business.sysUser.request.UserRegisterRequest;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.Map;

public interface SysUserService {
    /* 管理后台登录 */
    Map<String, Object> login(LoginRequest request);

    /* 注册 */
    ResultModel register(UserRegisterRequest request);

    /* 用户登录 */
    Map<String, Object> frontLogin(LoginRequest request);
}
