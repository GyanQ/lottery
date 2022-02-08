package com.vietnam.lottery.business.sysUserRoleRelation.service.impl;

import com.vietnam.lottery.business.sysUserRoleRelation.entity.SysUserRoleRelation;
import com.vietnam.lottery.business.sysUserRoleRelation.mapper.SysUserRoleRelationMapper;
import com.vietnam.lottery.business.sysUserRoleRelation.request.UserRoleAddRequest;
import com.vietnam.lottery.business.sysUserRoleRelation.service.SysUserRoleRelationService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色信息(SysUserRoleRelation)表服务实现类
 *
 * @author Gyan
 * @since 2022-01-28 15:11:44
 */
@Service("sysUserRoleRelationService")
public class SysUserRoleRelationServiceImpl implements SysUserRoleRelationService {
    @Autowired
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(UserRoleAddRequest request) {
        SysUserRoleRelation userRoleRelation = new SysUserRoleRelation();
        userRoleRelation.setUserId(request.getUserId());
        userRoleRelation.setRoleId(request.getRoleId());
        userRoleRelation.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysUserRoleRelationMapper.insert(userRoleRelation));
    }
}

