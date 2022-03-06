package com.vietnam.lottery.business.unpackRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UnPackAddRequest implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "奖项")
    @NotBlank(message = "奖项不能为空")
    private String name;

    @ApiModelProperty(value = "区间开始值")
    @NotNull(message = "区间开始值不能为空")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    @NotNull(message = "区间结束值不能为空")
    private Integer end;

    @ApiModelProperty(value = "中奖概率")
    @NotNull(message = "中奖概率不能为空")
    private Long probability;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
