package com.vietnam.lottery.business.sysUserRoleRelation.service.impl;

import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
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
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(UserRoleAddRequest request) {
        SysUserRoleRelation userRoleRelation = new SysUserRoleRelation();
        userRoleRelation.setUserId(request.getUserId());
        userRoleRelation.setRoleId(request.getRoleId());
        userRoleRelation.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("新增");
        record.setContent("分配后台账号角色");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysUserRoleRelationMapper.insert(userRoleRelation));
    }
}

