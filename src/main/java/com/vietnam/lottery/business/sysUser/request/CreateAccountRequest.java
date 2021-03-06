package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateAccountRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
