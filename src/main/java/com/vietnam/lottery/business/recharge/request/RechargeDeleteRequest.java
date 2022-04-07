package com.vietnam.lottery.business.recharge.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RechargeDeleteRequest implements Serializable {
    private static final long serialVersionUID = -8307862560211601862L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(hidden = true)
    private String deleteBy;
}
