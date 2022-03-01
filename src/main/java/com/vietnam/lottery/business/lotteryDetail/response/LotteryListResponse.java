package com.vietnam.lottery.business.lotteryDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LotteryListResponse implements Serializable {
    private static final long serialVersionUID = -4915813726129006929L;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "红包id")
    private Long redPacketsId;

    @ApiModelProperty(value = "奖项")
    private String awards;

    @ApiModelProperty(value = "金额")
    private Long amount;

    @ApiModelProperty(value = "开奖时间")
    private String createDate;
}
