package com.vietnam.lottery.business.grabRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BetRequest implements Serializable {
    private static final long serialVersionUID = 6100185547611792135L;

    @ApiModelProperty(value = "抢红包id")
    private String id;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
