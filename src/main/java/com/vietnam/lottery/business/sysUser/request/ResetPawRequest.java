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

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
