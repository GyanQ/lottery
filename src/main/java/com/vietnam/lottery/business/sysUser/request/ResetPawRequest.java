package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ResetPawRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;


    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
