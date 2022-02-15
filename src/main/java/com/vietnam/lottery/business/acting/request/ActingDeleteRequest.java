package com.vietnam.lottery.business.acting.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActingDeleteRequest implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(hidden = true)
    private Long deleteBy;
}
