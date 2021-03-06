package com.vietnam.lottery.business.rechargeDetail.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = 5187068231705462414L;

    @ApiModelProperty(value = "类型:1zalo 2momo")
    private String type;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "订单编号")
    private String orderId;
}
