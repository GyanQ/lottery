package com.vietnam.lottery.business.actingCommissionDetail.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LowerLevelListRequest implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private String userId;
}
