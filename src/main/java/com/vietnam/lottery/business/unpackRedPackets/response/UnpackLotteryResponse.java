package com.vietnam.lottery.business.unpackRedPackets.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UnpackLotteryResponse implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "奖项名称")
    private String name;

    @ApiModelProperty(value = "中奖金额")
    private BigDecimal amount;
}
