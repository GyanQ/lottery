package com.vietnam.lottery.business.sysUserAccount.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CommissionListResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "累计分佣金额")
    private BigDecimal commissionAmount;
}
