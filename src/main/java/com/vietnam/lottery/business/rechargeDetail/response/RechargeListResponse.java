package com.vietnam.lottery.business.rechargeDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeListResponse implements Serializable {
    private static final long serialVersionUID = 6887012534459921975L;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "充值时间")
    private String createDate;
}
