package com.vietnam.lottery.business.basicIndicators.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class IndicatorsRequest implements Serializable {

    private static final long serialVersionUID = -5949557716110575316L;

    @ApiModelProperty(value = "开始时间")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;
}
