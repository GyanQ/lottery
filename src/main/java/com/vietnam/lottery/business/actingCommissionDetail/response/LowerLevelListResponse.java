package com.vietnam.lottery.business.actingCommissionDetail.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LowerLevelListResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;


    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "代理级别")
    private String actingLevel;

    @ApiModelProperty(value = "消费金额")
    private Long amount;

    @ApiModelProperty(value = "分佣金额")
    private Long commissionAmount;

    @ApiModelProperty(value = "注册时间")
    private String createDate;
}
