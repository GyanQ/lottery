package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PawFreeLoginRequest implements Serializable {
    private static final long serialVersionUID = 4963585515216125019L;

    private String userId;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(hidden = true)
    private String ip;

    @ApiModelProperty(value = "语言类型(0英文 1越南)")
    @NotBlank(message = "语言类型不能为空")
    private String type;

}
