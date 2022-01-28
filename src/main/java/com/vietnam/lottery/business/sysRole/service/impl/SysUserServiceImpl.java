package com.vietnam.lottery.business.sysRole.service.impl;

import com.vietnam.lottery.business.sysRole.entity.SysRole;
import com.vietnam.lottery.business.sysRole.mapper.SysRoleMapper;
import com.vietnam.lottery.business.sysRole.service.SysRoleService;
import com.vietnam.lottery.common.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public ResultModel add(SysRole sysRole) {
        return null;
    }
}
