package com.vietnam.lottery.business.grabRedPackets.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DetailResponse implements Serializable {
    private static final long serialVersionUID = 6100185547611792135L;

    @ApiModelProperty(value = "抢红包下注金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "区间开始值")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    private Integer end;
}
