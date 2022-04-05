package com.vietnam.lottery.business.withdraw.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WithdrawRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -781612067551578849L;

    @ApiModelProperty(value = "提现金额")
    @NotNull(message = "提现金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现账号id")
    @NotBlank(message = "提现账号id不能为空")
    private String withdrawId;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
