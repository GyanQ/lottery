package com.vietnam.lottery.business.sysUserAccount.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WithdrawRequest implements Serializable {
    private static final long serialVersionUID = -781612067551578849L;

    @ApiModelProperty(value = "提现金额")
    @NotNull(message = "提现金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty(value = "银行卡id")
    @NotBlank(message = "银行卡id不能为空")
    private String bankCardId;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
