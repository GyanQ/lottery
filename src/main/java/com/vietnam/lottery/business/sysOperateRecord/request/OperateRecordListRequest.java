package com.vietnam.lottery.business.sysOperateRecord.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OperateRecordListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 8054291259651716613L;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd 00:00:00", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd 59:59:59", timezone = "GMT+8")
    private String endDate;
}
