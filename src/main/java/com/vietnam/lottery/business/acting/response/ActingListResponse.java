package com.vietnam.lottery.business.acting.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActingListResponse implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "代理等级")
    private String level;

    @ApiModelProperty(value = "分佣比例")
    private Long commissionRatio;
}
