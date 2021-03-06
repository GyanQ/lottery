package com.vietnam.lottery.business.sysUser.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GrabRedPacketsListResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "红包id")
    private String id;

    @ApiModelProperty(value = "投注金额")
    private Long amount;

    @ApiModelProperty(value = "中奖金额")
    private Long winningAmount;

    @ApiModelProperty(value = "投注时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

    @ApiModelProperty(value = "开奖时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String lotteryAmount;
}
