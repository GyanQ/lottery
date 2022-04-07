package com.vietnam.lottery.business.recharge.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeListResponse implements Serializable {

    private static final long serialVersionUID = 6498934194203851454L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;
}
