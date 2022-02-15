package com.vietnam.lottery.business.acting.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActingAddRequest implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "代理等级")
    @NotBlank(message = "代理等级不能为空")
    private String level;

    @ApiModelProperty(value = "分佣比例")
    @NotNull(message = "分佣比例不能为空")
    private BigDecimal commissionRatio;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
