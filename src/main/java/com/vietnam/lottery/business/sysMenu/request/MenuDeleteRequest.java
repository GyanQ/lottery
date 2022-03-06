package com.vietnam.lottery.business.sysMenu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuDeleteRequest implements Serializable {
    private static final long serialVersionUID = 8480077422914617604L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private String id;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
