package com.vietnam.lottery.business.recharge.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.recharge.entity.Recharge;
import com.vietnam.lottery.business.recharge.mapper.RechargeMapper;
import com.vietnam.lottery.business.recharge.request.RechargeAddRequest;
import com.vietnam.lottery.business.recharge.request.RechargeDeleteRequest;
import com.vietnam.lottery.business.recharge.request.RechargeListRequest;
import com.vietnam.lottery.business.recharge.request.RechargeUpdateRequest;
import com.vietnam.lottery.business.recharge.response.RechargeListResponse;
import com.vietnam.lottery.business.recharge.service.RechargeService;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 充值配置(Recharge)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-07 23:28:19
 */
@Service("rechargeService")
@Slf4j
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(RechargeAddRequest request) {
        Recharge recharge = new Recharge();
        recharge.setAmount(request.getAmount());
        recharge.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("充值配置");
        record.setOperate("新增");
        record.setContent("新增充值配置");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(rechargeMapper.insert(recharge));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(RechargeUpdateRequest request) {
        Recharge recharge = rechargeMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(recharge)) return ResultUtil.failure("数据不存在,修改失败");
        recharge.setAmount(request.getAmount());
        recharge.setUpdateBy(request.getUpdateBy());
        recharge.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("充值配置");
        record.setOperate("修改");
        record.setContent("修改充值配置");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(rechargeMapper.updateById(recharge));
    }

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<Recharge> page = new Page<>(request.getCurrent(), request.getSize());
        Page<Recharge> iPage = rechargeMapper.selectPage(page, new QueryWrapper<Recharge>().eq("del_flag", DelFlagEnum.CODE.getCode()));

        Page<RechargeListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<RechargeListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        iPage.getRecords().forEach(o -> {
            RechargeListResponse resp = new RechargeListResponse();
            resp.setId(o.getId());
            resp.setAmount(o.getAmount());
            list.add(resp);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(RechargeDeleteRequest request) {
        Recharge recharge = rechargeMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(recharge)) return ResultUtil.failure("数据不存在,删除失败");

        recharge.setDelFlag(DelFlagEnum.MESSAGE.getCode());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("充值配置");
        record.setOperate("删除");
        record.setContent("删除充值配置");
        record.setCreateBy(request.getDeleteBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(rechargeMapper.updateById(recharge));
    }
}

