package com.vietnam.lottery.content.back.sysOperateRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.request.OperateRecordListRequest;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "操作记录")
@RequestMapping("/sys/operateRecord")
public class SysOperateRecordController {
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<SysOperateRecord>> list(@RequestBody OperateRecordListRequest request) {
        return ResultUtil.success(sysOperateRecordService.list(request));
    }
}
