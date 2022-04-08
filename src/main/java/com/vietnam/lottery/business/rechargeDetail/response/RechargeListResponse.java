package com.vietnam.lottery.business.rechargeDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RechargeListResponse implements Serializable {
    private static final long serialVersionUID = 6887012534459921975L;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "充值金额")
    private Long amount;

    @ApiModelProperty(value = "充值时间")
    private String createDate;

    @ApiModelProperty(value = "支付状态(1待支付 2已支付 3取消支付)")
    private String status;
}
