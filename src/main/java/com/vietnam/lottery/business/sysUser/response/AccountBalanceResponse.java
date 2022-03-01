package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountBalanceResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "账户余额")
    private Long amount;

    @ApiModelProperty(value = "分佣金额")
    private Long commissionBalanceAmount;

    @ApiModelProperty(value = "红包余额")
    private Long redEnvelopeAmount;
}
