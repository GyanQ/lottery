package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class KeepListResponse implements Serializable {

    private static final long serialVersionUID = -3087995089154940157L;

    @ApiModelProperty(value = "次留")
    private Integer secondStay;

    @ApiModelProperty(value = "次留百分率")
    private Integer secondPer;

    @ApiModelProperty(value = "3留")
    private Integer three;

    @ApiModelProperty(value = "3留百分率")
    private Integer threePer;

    @ApiModelProperty(value = "7留")
    private Integer sevenStay;

    @ApiModelProperty(value = "7留百分率")
    private Integer sevenStayPer;

    @ApiModelProperty(value = "15留")
    private Integer fifteenStay;

    @ApiModelProperty(value = "15留百分率")
    private Integer fifteenStayPer;

    @ApiModelProperty(value = "月留")
    private Integer monthStay;

    @ApiModelProperty(value = "月留百分率")
    private Integer monthStayPer;
}
