package com.vietnam.lottery.business.sysUser.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GrabRedPacketsListResponse extends PageRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "红包id")
    private Long id;

    @ApiModelProperty(value = "投注金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "中奖金额")
    private BigDecimal winningAmount;

    @ApiModelProperty(value = "投注时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

    @ApiModelProperty(value = "开奖时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String lotteryAmount;
}
