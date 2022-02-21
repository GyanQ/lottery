package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class IndicatorsResponse implements Serializable {

    private static final long serialVersionUID = -7283739225157571577L;

    @ApiModelProperty(value = "访客总数")
    private Integer visitorsTotalNumber;

    @ApiModelProperty(value = "新增访客")
    private Integer newVisitors;

    @ApiModelProperty(value = "登陆总数")
    private Integer totalLogins;

    @ApiModelProperty(value = "新增注册")
    private Integer newRegistration;

    @ApiModelProperty(value = "抢红包数")
    private Integer envelopesNumber;

    @ApiModelProperty(value = "抢红包总额")
    private BigDecimal envelopesTotalAmount;

    @ApiModelProperty(value = "拆红包数")
    private Integer envelopesRemoveNumber;

    @ApiModelProperty(value = "开奖总额")
    private BigDecimal totalPrize;

    @ApiModelProperty(value = "充值总额")
    private BigDecimal totalRecharge;

    @ApiModelProperty(value = "提现总额")
    private BigDecimal totalWithdrawal;
}
