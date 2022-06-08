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

    @ApiModelProperty(value = "推广渠道必传推广人userId")
    private String id;

    @ApiModelProperty(hidden = true)
    private String ip;

    @ApiModelProperty(value = "语言类型(0英文 1越南)")
    @NotBlank(message = "语言类型不能为空")
    private String type;

}
