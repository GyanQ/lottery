package com.vietnam.lottery.business.unpackRedPackets.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UnPackListResponse implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "奖项")
    private String name;

    @ApiModelProperty(value = "区间开始值")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    private Integer end;

    @ApiModelProperty(value = "中奖概率")
    private BigDecimal probability;
}
