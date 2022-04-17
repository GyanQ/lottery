package com.vietnam.lottery.business.rechargeDetail.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectOrderRequest implements Serializable {
    private static final long serialVersionUID = -5519562959972459212L;

    @ApiModelProperty(value = "抢红包id")
    private String id;

    @ApiModelProperty(value = "支付凭证")
    private String ticket;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
