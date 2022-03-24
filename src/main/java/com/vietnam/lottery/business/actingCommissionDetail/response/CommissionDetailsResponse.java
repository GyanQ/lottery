package com.vietnam.lottery.business.actingCommissionDetail.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommissionDetailsResponse implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "分佣总额")
    private Long total;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String date;

    @ApiModelProperty(value = "佣金")
    private Long amount;
}
