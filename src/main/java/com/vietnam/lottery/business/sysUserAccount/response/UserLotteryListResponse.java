package com.vietnam.lottery.business.sysUserAccount.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserLotteryListResponse implements Serializable {
    private static final long serialVersionUID = -4915813726129006929L;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "红包id")
    private String redPacketsId;

    @ApiModelProperty(value = "红包金额")
    private BigDecimal redPacketsAmount;

    @ApiModelProperty(value = "奖项")
    private String awards;

    @ApiModelProperty(value = "金额")
    private Long amount;

    @ApiModelProperty(value = "开奖时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;
}
