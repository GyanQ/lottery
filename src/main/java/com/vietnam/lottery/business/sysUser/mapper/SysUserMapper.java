package com.vietnam.lottery.business.sysUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.response.MenuPermissionResponse;
import com.vietnam.lottery.business.sysUser.response.UserGetPermissionResponse;
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
    UserGetPermissionResponse selectRoleName(@Param("id") Long id);

    /* 查询角色菜单权限 */
    List<MenuPermissionResponse> selectMenuPermission(@Param("id") Long id);

    /* 查询当前账号是否是超级管理员 */
    Boolean isSuperAdmin(@Param("name") String name);
}

