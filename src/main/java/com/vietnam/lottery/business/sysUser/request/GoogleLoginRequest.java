package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GoogleLoginRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "userId")
    @NotNull(message = "userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;
}
