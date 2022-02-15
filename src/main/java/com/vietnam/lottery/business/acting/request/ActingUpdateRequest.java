package com.vietnam.lottery.business.acting.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActingUpdateRequest implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "代理等级")
    private String level;

    @ApiModelProperty(value = "分佣比例")
    private BigDecimal commissionRatio;

    @ApiModelProperty(hidden = true)
    private Long updateBy;
}
