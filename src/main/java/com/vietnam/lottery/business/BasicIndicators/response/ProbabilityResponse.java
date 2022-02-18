package com.vietnam.lottery.business.BasicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProbabilityResponse {

    @ApiModelProperty(value = "奖项")
    private String name;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "中奖概率")
    private BigDecimal probability;
}
