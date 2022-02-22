package com.vietnam.lottery.business.actingCommissionDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActingDetailListResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = " 代理id")
    private Long actingId;

    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "累计分佣金额")
    private BigDecimal commissionAmount;
}
