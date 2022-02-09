package com.vietnam.lottery.business.sysOperateRecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.mapper.SysOperateRecordMapper;
import com.vietnam.lottery.business.sysOperateRecord.request.OperateRecordListRequest;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作记录(SysOperateRecord)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-09 10:45:32
 */
@Service("sysOperateRecordService")
public class SysOperateRecordServiceImpl extends ServiceImpl<SysOperateRecordMapper, SysOperateRecord> implements SysOperateRecordService {
    @Autowired
    private SysOperateRecordMapper sysOperateRecordMapper;

    @Override
    public ResultModel add(SysOperateRecord request) {
        SysOperateRecord record = new SysOperateRecord();
        record.setModule(request.getModule());
        record.setContent(request.getContent());
        record.setOperate(request.getOperate());
        record.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysOperateRecordMapper.insert(record));
    }

    @Override
    public Page<SysOperateRecord> List(OperateRecordListRequest request) {
        Page<SysOperateRecord> page = new Page<>(request.getCurrent(), request.getSize());
        QueryWrapper<SysOperateRecord> query = new QueryWrapper<>();
        if (null != request.getBeginDate() && null != request.getEndDate()) {
            query.ge("create_date", request.getBeginDate()).le("create_date", request.getEndDate());
        } else {
            //当前时间
            String date = DateUtils.getCurrentTimeStr(DateUtils.DATE_PATTERN);
            query.ge("create_date", date).le("create_date", date);
        }
        query.orderByDesc("create_date");
        sysOperateRecordMapper.selectPage(page, query);
        return page;
    }
}
