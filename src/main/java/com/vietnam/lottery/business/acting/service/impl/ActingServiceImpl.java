package com.vietnam.lottery.business.acting.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.acting.entity.Acting;
import com.vietnam.lottery.business.acting.mapper.ActingMapper;
import com.vietnam.lottery.business.acting.request.ActingAddRequest;
import com.vietnam.lottery.business.acting.request.ActingDeleteRequest;
import com.vietnam.lottery.business.acting.request.ActingListRequest;
import com.vietnam.lottery.business.acting.request.ActingUpdateRequest;
import com.vietnam.lottery.business.acting.response.ActingDetailResponse;
import com.vietnam.lottery.business.acting.response.ActingListResponse;
import com.vietnam.lottery.business.acting.service.ActingService;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理配置(Acting)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-15 16:24:15
 */
@Service("actingService")
public class ActingServiceImpl implements ActingService {
    @Resource
    private ActingMapper actingMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(ActingAddRequest request) {
        Integer count = actingMapper.selectCount(new QueryWrapper<Acting>().eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (count >= 3) {
            return ResultUtil.failure("最多添加三级代理");
        }
        Acting acting = new Acting();
        acting.setLevel(request.getLevel());
        acting.setCommissionRatio(request.getCommissionRatio());
        acting.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("代理管理");
        record.setOperate("新增");
        record.setContent("新增代理角色");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(actingMapper.insert(acting));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(ActingUpdateRequest request) {
        Integer count = actingMapper.selectCount(new QueryWrapper<Acting>().eq("del_flag", DelFlagEnum.CODE.getCode()).ne("id", request.getId()));
        if (count >= 3) {
            return ResultUtil.failure("最多添加三级代理");
        }
        Acting acting = actingMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(acting)) return ResultUtil.failure("fail to edit");

        acting.setCommissionRatio(request.getCommissionRatio());
        acting.setLevel(request.getLevel());
        acting.setUpdateBy(request.getUpdateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("代理管理");
        record.setOperate("修改");
        record.setContent("修改代理角色");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(actingMapper.updateById(acting));
    }

    @Override
    public Page<ActingListResponse> list(ActingListRequest request) {
        Page<Acting> page = new Page<>(request.getCurrent(), request.getSize());
        Page<Acting> iPage = actingMapper.selectPage(page, new QueryWrapper<Acting>().eq("del_flag", DelFlagEnum.CODE.getCode()));

        Page<ActingListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<ActingListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;
        iPage.getRecords().forEach(o -> {
            ActingListResponse resp = new ActingListResponse();
            resp.setId(o.getId());
            resp.setLevel(o.getLevel());
            resp.setCommissionRatio(o.getCommissionRatio());
            list.add(resp);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(ActingDeleteRequest request) {
        Acting acting = actingMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(acting)) return ResultUtil.failure("fail to delete");

        acting.setId(request.getId());
        acting.setDelFlag(DelFlagEnum.MESSAGE.getCode());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("代理管理");
        record.setOperate("删除");
        record.setContent("删除代理角色");
        record.setCreateBy(request.getDeleteBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(actingMapper.updateById(acting));
    }

    @Override
    public ActingDetailResponse detail(Long id) {
        QueryWrapper<Acting> query = new QueryWrapper<>();
        query.eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode());
        Acting acting = actingMapper.selectOne(query);
        if (ObjectUtil.isEmpty(acting)) throw new GlobalException("Agent role does not exist");

        ActingDetailResponse resp = new ActingDetailResponse();
        resp.setLevel(acting.getLevel());
        resp.setCommissionRatio(acting.getCommissionRatio());
        return resp;
    }
}

