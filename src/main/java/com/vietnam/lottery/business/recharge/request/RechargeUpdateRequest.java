package com.vietnam.lottery.business.recharge.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeUpdateRequest implements Serializable {
    private static final long serialVersionUID = -4912446986653778627L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
