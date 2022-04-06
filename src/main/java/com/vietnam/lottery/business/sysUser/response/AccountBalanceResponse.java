package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountBalanceResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "分佣金额")
    private BigDecimal commissionBalanceAmount;

    @ApiModelProperty(value = "拆红包余额")
    private BigDecimal redEnvelopeAmount;

    @ApiModelProperty(value = "提现余额")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty(value = "充值余额")
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "抢红包余额")
    private BigDecimal grabAmount;
}
