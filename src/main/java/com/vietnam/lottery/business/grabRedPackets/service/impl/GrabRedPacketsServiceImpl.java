package com.vietnam.lottery.business.grabRedPackets.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.entity.GrabRedPackets;
import com.vietnam.lottery.business.grabRedPackets.mapper.GrabRedPacketsMapper;
import com.vietnam.lottery.business.grabRedPackets.request.AddRequest;
import com.vietnam.lottery.business.grabRedPackets.request.DeleteRequest;
import com.vietnam.lottery.business.grabRedPackets.request.ListRequest;
import com.vietnam.lottery.business.grabRedPackets.request.UpdateRequest;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.business.grabRedPackets.service.GrabRedPacketsService;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 抢红包(GrabRedPackets)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 18:00:22
 */
@Service("grabRedPacketsService")
public class GrabRedPacketsServiceImpl implements GrabRedPacketsService {
    @Autowired
    private GrabRedPacketsMapper grabRedPacketsMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(AddRequest request) {
        GrabRedPackets grabRedPackets = new GrabRedPackets();
        grabRedPackets.setAmount(request.getAmount());
        grabRedPackets.setIntervalBeginValue(request.getBegin());
        grabRedPackets.setIntervalEndValue(request.getEnd());
        grabRedPackets.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("抢红包配置");
        record.setOperate("新增");
        record.setContent("新增抢红包配置");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(grabRedPacketsMapper.insert(grabRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(UpdateRequest request) {
        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("查询不到该条配置,修改失败！");

        grabRedPackets.setAmount(request.getAmount());
        grabRedPackets.setIntervalBeginValue(request.getBegin());
        grabRedPackets.setIntervalEndValue(request.getEnd());
        grabRedPackets.setUpdateBy(request.getUpdateBy());
        grabRedPackets.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("抢红包配置");
        record.setOperate("修改");
        record.setContent("修改抢红包配置");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(grabRedPacketsMapper.updateById(grabRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(DeleteRequest request) {
        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("查询不到该条配置,删除失败！");

        grabRedPackets.setDelFlag(DelFlagEnum.MESSAGE.getCode());
        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("抢红包配置");
        record.setOperate("删除");
        record.setContent("删除抢红包配置");
        record.setCreateBy(request.getDeleteBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(grabRedPacketsMapper.updateById(grabRedPackets));
    }

    @Override
    public DetailResponse detail(Long id) {
        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectOne(new QueryWrapper<GrabRedPackets>().eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode()));
        DetailResponse response = new DetailResponse();
        if (ObjectUtil.isEmpty(grabRedPackets)) return response;
        response.setBegin(grabRedPackets.getIntervalBeginValue());
        response.setEnd(grabRedPackets.getIntervalEndValue());
        response.setAmount(grabRedPackets.getAmount());
        return response;
    }

    @Override
    public Page<ListResponse> list(ListRequest request) {
        Page<GrabRedPackets> page = new Page<>(request.getCurrent(), request.getSize());
        Page<GrabRedPackets> iPage = grabRedPacketsMapper.selectPage(page, new QueryWrapper<GrabRedPackets>().eq("del_flag", DelFlagEnum.CODE.getCode()));

        Page<ListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<ListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;
        iPage.getRecords().forEach(o -> {
            ListResponse resp = new ListResponse();
            resp.setAmount(o.getAmount());
            resp.setId(o.getId());
            resp.setBegin(o.getIntervalBeginValue());
            resp.setEnd(o.getIntervalEndValue());
            list.add(resp);
        });
        responsePage.setRecords(list);
        return responsePage;
    }
}

