package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SendSmsRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "手机")
    @NotBlank(message = "手机不能为空")
    private String phone;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
