package com.vietnam.lottery.business.unpackRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UnPackUpdateRequest implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "奖项")
    private String name;

    @ApiModelProperty(value = "区间开始值")
    private Integer begin;

    @ApiModelProperty(value = "区间结束值")
    private Integer end;

    @ApiModelProperty(value = "中奖概率")
    private Long probability;

    @ApiModelProperty(hidden = true)
    private Long updateBy;
}
