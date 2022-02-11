package com.vietnam.lottery.business.sysOperateRecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.request.OperateRecordListRequest;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 操作记录(SysOperateRecord)表服务接口
 *
 * @author Gyan
 * @since 2022-02-09 10:45:31
 */
public interface SysOperateRecordService extends IService<SysOperateRecord> {
    /* 新增 */
    ResultModel add(SysOperateRecord request);

    /* 列表 */
    Page<SysOperateRecord> list(OperateRecordListRequest request);
}

