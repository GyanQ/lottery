package com.vietnam.lottery.business.acting.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActingUpdateRequest implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "代理等级")
    private String level;

    @ApiModelProperty(value = "分佣比例")
    private Long commissionRatio;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
