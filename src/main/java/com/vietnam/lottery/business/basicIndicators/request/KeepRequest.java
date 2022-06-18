package com.vietnam.lottery.business.basicIndicators.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KeepRequest implements Serializable {

    private static final long serialVersionUID = 1554835631571741239L;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String beginDate;
}
