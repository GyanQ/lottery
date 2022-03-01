package com.vietnam.lottery.business.grabRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddRequest implements Serializable {
    private static final long serialVersionUID = 6100185547611792135L;

    @ApiModelProperty(value = "抢红包下注金额")
    @NotNull(message = "下注金额不能为空")
    private Long amount;

    @ApiModelProperty(value = "区间开始值")
    @NotNull(message = "区间开始值不能为空")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    @NotNull(message = "区间结束值不能为空")
    private Integer end;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
