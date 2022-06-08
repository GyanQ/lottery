package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class GrabResponse implements Serializable {

    private static final long serialVersionUID = 1154048350591550556L;

    @ApiModelProperty(value = "抢红包金额")
    private BigDecimal amount;

    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(value = "拆红包")
    private List<UnpackResponse> unpackList = new ArrayList<>();
}
