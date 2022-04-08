package com.vietnam.lottery.business.grabRedPackets.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.entity.GrabRedPackets;
import com.vietnam.lottery.business.grabRedPackets.mapper.GrabRedPacketsMapper;
import com.vietnam.lottery.business.grabRedPackets.request.*;
import com.vietnam.lottery.business.grabRedPackets.response.DetailResponse;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.business.grabRedPackets.service.GrabRedPacketsService;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抢红包(GrabRedPackets)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 18:00:22
 */
@Service("grabRedPacketsService")
@Slf4j
public class GrabRedPacketsServiceImpl implements GrabRedPacketsService {
    @Autowired
    private GrabRedPacketsMapper grabRedPacketsMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Resource
    private RechargeDetailMapper rechargeDetailMapper;
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(AddRequest request) {
        if (request.getAmount().compareTo(new BigDecimal(50000)) == -1) {
            return ResultUtil.failure("金额不能小于50000盾");
        }
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
        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("fail to edit");

        if (request.getAmount().compareTo(new BigDecimal(50000)) == -1) {
            return ResultUtil.failure("金额不能小于50000盾");
        }
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
        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("查询不到数据,删除失败");

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel bet(BetRequest request) {
        //用户支入金额
        BigDecimal incomeAmount = BigDecimal.ZERO;
        //用户支出金额
        BigDecimal expenditureAmount = BigDecimal.ZERO;
        //提现余额
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        //查询用户余额
        Map<String, Map<String, BigDecimal>> map = sysUserAccountMapper.getByIdAmount(request.getCreateBy());
        for (Map<String, BigDecimal> value : map.values()) {
            incomeAmount = value.get("incomeAmount");
            expenditureAmount = value.get("expenditureAmount");
            withdrawAmount = value.get("withdrawAmount");
        }
        //用户充值余额
        BigDecimal rechargeTotal = rechargeDetailMapper.getByIdRecharge(request.getCreateBy());
        //用户总余额
        BigDecimal userAmount = rechargeTotal.add(incomeAmount).subtract(expenditureAmount).subtract(withdrawAmount);

        //查询抢红包余额
        GrabRedPackets redPackets = grabRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(redPackets)) throw new GlobalException("Can't find the red envelope information");

        if (userAmount.compareTo(redPackets.getAmount()) == -1) {
            throw new GlobalException("Insufficient balance please recharge");
        }

        //生成订单号
        String date = DateUtils.getCurrentTimeStr(DateUtils.UNSIGNED_DATETIME_PATTERN);
        String orderNo = request.getCreateBy().toString() + date;

        //用户余额记录
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setId(orderNo);
        sysUserAccount.setProductId(redPackets.getId());
        sysUserAccount.setType("2");
        sysUserAccount.setSpending("1");
        sysUserAccount.setAmount(redPackets.getAmount());
        return ResultUtil.success(sysUserAccountMapper.insert(sysUserAccount));
    }
}

