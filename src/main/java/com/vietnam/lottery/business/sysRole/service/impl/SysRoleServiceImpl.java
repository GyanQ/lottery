package com.vietnam.lottery.business.sysRole.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysRole.entity.SysRole;
import com.vietnam.lottery.business.sysRole.mapper.SysRoleMapper;
import com.vietnam.lottery.business.sysRole.request.RoleAddRequest;
import com.vietnam.lottery.business.sysRole.request.RoleListRequest;
import com.vietnam.lottery.business.sysRole.request.RoleUpdateRequest;
import com.vietnam.lottery.business.sysRole.response.RoleDetailResponse;
import com.vietnam.lottery.business.sysRole.response.RoleListResponse;
import com.vietnam.lottery.business.sysRole.service.SysRoleService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(RoleAddRequest request) {
        SysRole role = new SysRole();
        role.setName(request.getName());
        role.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("角色管理");
        record.setOperate("新增");
        record.setContent("新增角色身份");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysRoleMapper.insert(role));
    }

    @Override
    public Page<RoleListResponse> list(RoleListRequest request) {
        Page<SysRole> page = new Page<>(request.getCurrent(), request.getSize());
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        query.like(null != request.getName(), "name", request.getName());
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        query.orderByAsc("create_date");
        Page<SysRole> iPage = sysRoleMapper.selectPage(page, query);
        Page<RoleListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        List<RoleListResponse> list = new ArrayList<>();
        iPage.getRecords().forEach(o -> {
            RoleListResponse response = new RoleListResponse();
            response.setId(o.getId());
            response.setName(o.getName());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(RoleUpdateRequest request) {
        SysRole role = sysRoleMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(role)) {
            return ResultUtil.failure("无法查询到此数据!");
        }
        role.setName(request.getName());
        role.setUpdateBy(request.getUpdateBy());
        role.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("角色管理");
        record.setOperate("修改");
        record.setContent("修改角色身份");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysRoleMapper.updateById(role));
    }

    @Override
    public RoleDetailResponse detail(Long id) {
        SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", id));
        if (ObjectUtil.isEmpty(role)) throw new GlobalException("该信息不存在");

        RoleDetailResponse response = new RoleDetailResponse();
        response.setName(role.getName());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(MenuDeleteRequest request) {
        SysRole sysRole = sysRoleMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(sysRole)) return ResultUtil.failure("查询不到该角色信息,删除失败");
        sysRole.setDelFlag(DelFlagEnum.MESSAGE.getCode());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("角色管理");
        record.setOperate("删除");
        record.setContent("删除角色身份");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysRoleMapper.updateById(sysRole));
    }
}
