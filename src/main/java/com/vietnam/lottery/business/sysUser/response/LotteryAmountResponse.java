package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LotteryAmountResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "开奖金额")
    private BigDecimal amount;
}
