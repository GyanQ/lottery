package com.vietnam.lottery.business.withdrawDetail.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.withdrawDetail.entity.WithdrawDetail;
import com.vietnam.lottery.business.withdrawDetail.mapper.WithdrawDetailMapper;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawAuditRequest;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawListRequest;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawListResponse;
import com.vietnam.lottery.business.withdrawDetail.service.WithdrawDetailService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现记录(WithdrawDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 10:50:09
 */
@Service("withdrawDetailService")
public class WithdrawDetailServiceImpl implements WithdrawDetailService {
    @Autowired
    private WithdrawDetailMapper withdrawDetailMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @Override
    public Page<WithdrawListResponse> list(WithdrawListRequest request) {
        Page<WithdrawListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return withdrawDetailMapper.list(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel audit(WithdrawAuditRequest request) {
        WithdrawDetail withdrawDetail = withdrawDetailMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(withdrawDetail)) return ResultUtil.failure("该条信息不存在,修改失败");

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("提现模块");
        record.setOperate("审核");
        record.setContent("审核提现记录");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        withdrawDetail.setAudit(request.getAudit());
        return ResultUtil.success(withdrawDetailMapper.updateById(withdrawDetail));
    }

    @Override
    public Page<WithdrawListResponse> withDrawInfo(WithdrawListRequest request) {
        Page<WithdrawDetail> page = new Page<>(request.getCurrent(), request.getSize());
        QueryWrapper<WithdrawDetail> query = new QueryWrapper<>();
        query.eq("create_by", request.getKeyWord()).eq("del_flag", DelFlagEnum.CODE.getCode());
        Page<WithdrawDetail> iPage = withdrawDetailMapper.selectPage(page, query);

        Page<WithdrawListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<WithdrawListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        iPage.getRecords().forEach(o -> {
            WithdrawListResponse response = new WithdrawListResponse();
            response.setCreateDate(DateUtils.dateConversionStr(o.getCreateDate(), DateUtils.DATETIME_PATTERN));
            response.setAmount(o.getAmount());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }
}

