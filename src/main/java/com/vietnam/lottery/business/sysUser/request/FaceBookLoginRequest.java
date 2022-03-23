package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class FaceBookLoginRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "userId")
    @NotBlank(message = "userId不能为空")
    private String userId;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;
}
