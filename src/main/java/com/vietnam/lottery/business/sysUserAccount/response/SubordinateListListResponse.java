package com.vietnam.lottery.business.sysUserAccount.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SubordinateListListResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;


    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "代理级别")
    private String actingLevel;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "分佣金额")
    private BigDecimal commissionAmount;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "注册时间")
    private String createDate;
}
