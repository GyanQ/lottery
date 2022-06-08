package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UnpackResponse implements Serializable {

    private static final long serialVersionUID = 1154048350591550556L;

    @ApiModelProperty(value = "奖项")
    private String name;

    @ApiModelProperty(value = "金额")
    private Long amount;

    @ApiModelProperty(value = "中奖概率")
    private Long probability;
}
