package com.vietnam.lottery.business.grabRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateRequest implements Serializable {
    private static final long serialVersionUID = 6100185547611792135L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "抢红包下注金额")
    private Long amount;

    @ApiModelProperty(value = "区间开始值")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    private Integer end;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
