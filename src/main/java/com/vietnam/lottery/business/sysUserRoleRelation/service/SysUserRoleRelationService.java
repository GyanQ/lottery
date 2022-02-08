package com.vietnam.lottery.business.sysUserRoleRelation.service;

import com.vietnam.lottery.business.sysUserRoleRelation.request.UserRoleAddRequest;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 角色信息(SysUserRoleRelation)表服务接口
 *
 * @author Gyan
 * @since 2022-01-28 15:11:43
 */
public interface SysUserRoleRelationService {

    ResultModel add(UserRoleAddRequest request);
}

