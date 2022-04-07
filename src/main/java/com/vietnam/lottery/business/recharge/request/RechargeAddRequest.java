package com.vietnam.lottery.business.recharge.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeAddRequest implements Serializable {
    private static final long serialVersionUID = -4912446986653778627L;

    @ApiModelProperty(value = "余额")
    @NotNull(message = "余额不能为空")
    private BigDecimal amount;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
