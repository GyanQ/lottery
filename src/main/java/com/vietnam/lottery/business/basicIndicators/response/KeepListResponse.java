package com.vietnam.lottery.business.basicIndicators.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KeepListResponse implements Serializable {

    private static final long serialVersionUID = -3087995089154940157L;

    @ApiModelProperty(value = "次留")
    private SecondStayResponse stayResponse = null;

    @ApiModelProperty(value = "3留")
    private TreeStayResponse treeStay = null;

    @ApiModelProperty(value = "7留")
    private SevenStayResponse sevenStay = null;

    @ApiModelProperty(value = "15留")
    private FifteenStayResponse fifteenStay = null;

    @ApiModelProperty(value = "月留")
    private MonthStayResponse monthStay = null;
}
