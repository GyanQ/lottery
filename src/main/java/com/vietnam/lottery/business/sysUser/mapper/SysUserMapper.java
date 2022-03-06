package com.vietnam.lottery.business.sysUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.request.GrabRedPacketsListRequest;
import com.vietnam.lottery.business.sysUser.request.UserListRequest;
import com.vietnam.lottery.business.sysUser.request.UserManageListRequest;
import com.vietnam.lottery.business.sysUser.response.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author Gyan
 * @since 2022-01-24 18:00:05
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /* 查询角色名称 */
    UserGetPermissionResponse selectRoleName(@Param("id") String id);

    /* 查询角色菜单权限 */
    List<MenuPermissionResponse>  selectMenuPermission(@Param("id") String id);

    /* 查询当前账号是否是超级管理员 */
    Boolean isSuperAdmin(@Param("name") String name);

    /* 账号管理列表 */
    Page<UserListResponse> list(@Param("page") Page page, @Param("request") UserListRequest request);

    /* 用户列表 */
    Page<UserManageListResponse> manageList(@Param("page") Page<UserManageListResponse> page, @Param("request") UserManageListRequest request);

    /* 红包记录 */
    Page<GrabRedPacketsListResponse> redPacketsList(@Param("page") Page page, @Param("request") GrabRedPacketsListRequest request);

    /* 用户详情 */
    UserDetailResponse detail(@Param("id") String id);
}

