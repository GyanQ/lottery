package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FifteenStayResponse implements Serializable {

    private static final long serialVersionUID = -3087995089154940157L;

    @ApiModelProperty(value = "留存百分比")
    private String keepPercentage;

    @ApiModelProperty(value = "留存用户数")
    private Integer userTotal;
}
