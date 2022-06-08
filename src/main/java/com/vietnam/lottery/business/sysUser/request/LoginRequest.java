package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "语言类型(0英文 1越南)")
    @NotBlank(message = "语言类型不能为空")
    private String type;

    @ApiModelProperty(hidden = true)
    private String ip;
}
