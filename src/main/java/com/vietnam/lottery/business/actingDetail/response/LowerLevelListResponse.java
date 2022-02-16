package com.vietnam.lottery.business.actingDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LowerLevelListResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;


    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "代理级别")
    private String actingLevel;

    @ApiModelProperty(value = "消费金额")
    private Long lowerLevelId;

    @ApiModelProperty(value = "分佣金额")
    private BigDecimal commissionAmount;

    @ApiModelProperty(value = "注册时间")
    private String createDate;
}