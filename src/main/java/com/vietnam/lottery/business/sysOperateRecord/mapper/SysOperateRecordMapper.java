package com.vietnam.lottery.business.sysOperateRecord.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作记录(SysOperateRecord)表数据库访问层
 *
 * @author Gyan
 * @since 2022-02-09 10:45:29
 */
@Mapper
public interface SysOperateRecordMapper extends BaseMapper<SysOperateRecord> {
}

