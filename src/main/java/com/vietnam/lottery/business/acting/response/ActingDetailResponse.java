package com.vietnam.lottery.business.acting.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActingDetailResponse implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "代理等级")
    private String level;

    @ApiModelProperty(value = "分佣比例")
    private BigDecimal commissionRatio;
}
