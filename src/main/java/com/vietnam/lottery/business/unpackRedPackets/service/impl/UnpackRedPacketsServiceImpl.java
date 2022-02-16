package com.vietnam.lottery.business.unpackRedPackets.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.unpackRedPackets.entity.UnpackRedPackets;
import com.vietnam.lottery.business.unpackRedPackets.mapper.UnpackRedPacketsMapper;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackAddRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackDeleteRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackListRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackUpdateRequest;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackDetailResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackListResponse;
import com.vietnam.lottery.business.unpackRedPackets.service.UnpackRedPacketsService;
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
 * 拆红包(UnpackRedPackets)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 18:00:37
 */
@Service("unpackRedPacketsService")
public class UnpackRedPacketsServiceImpl implements UnpackRedPacketsService {
    @Autowired
    private UnpackRedPacketsMapper unpackRedPacketsMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(UnPackAddRequest request) {
        UnpackRedPackets unpackRedPackets = new UnpackRedPackets();
        unpackRedPackets.setName(request.getName());
        unpackRedPackets.setIntervalBeginValue(request.getBegin());
        unpackRedPackets.setIntervalEndValue(request.getEnd());
        unpackRedPackets.setProbability(request.getProbability());
        unpackRedPackets.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("新增");
        record.setContent("新增拆红包配置");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.insert(unpackRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(UnPackUpdateRequest request) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(unpackRedPackets)) return ResultUtil.failure("该条信息不存在,修改失败");

        unpackRedPackets.setName(request.getName());
        unpackRedPackets.setIntervalBeginValue(request.getBegin());
        unpackRedPackets.setIntervalEndValue(request.getEnd());
        unpackRedPackets.setProbability(request.getProbability());
        unpackRedPackets.setUpdateBy(request.getUpdateBy());
        unpackRedPackets.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("修改");
        record.setContent("修改拆红包配置");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.updateById(unpackRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(UnPackDeleteRequest request) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(unpackRedPackets)) return ResultUtil.failure("该条信息不存在,删除失败");
        unpackRedPackets.setDelFlag(DelFlagEnum.MESSAGE.getCode());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("删除");
        record.setContent("删除拆红包配置");
        record.setCreateBy(request.getDeleteBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.updateById(unpackRedPackets));
    }

    @Override
    public UnPackDetailResponse detail(Long id) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectOne(new QueryWrapper<UnpackRedPackets>().eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode()));
        UnPackDetailResponse response = new UnPackDetailResponse();
        if (ObjectUtil.isEmpty(unpackRedPackets)) return response;

        response.setBegin(unpackRedPackets.getIntervalBeginValue());
        response.setEnd(unpackRedPackets.getIntervalEndValue());
        response.setProbability(unpackRedPackets.getProbability());
        response.setName(unpackRedPackets.getName());
        return response;
    }

    @Override
    public Page<UnPackListResponse> list(UnPackListRequest request) {
        Page<UnpackRedPackets> page = new Page<>(request.getCurrent(), request.getSize());
        Page<UnpackRedPackets> iPage = unpackRedPacketsMapper.selectPage(page, new QueryWrapper<UnpackRedPackets>().eq("del_flag", DelFlagEnum.CODE.getCode()));

        Page<UnPackListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<UnPackListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;
        iPage.getRecords().forEach(o -> {
            UnPackListResponse response = new UnPackListResponse();
            response.setId(o.getId());
            response.setBegin(o.getIntervalBeginValue());
            response.setEnd(o.getIntervalEndValue());
            response.setName(o.getName());
            response.setProbability(o.getProbability());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }
}

