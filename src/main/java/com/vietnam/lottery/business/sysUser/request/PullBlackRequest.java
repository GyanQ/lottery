package com.vietnam.lottery.business.sysUser.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PullBlackRequest implements Serializable {

    private static final long serialVersionUID = 3963546362419118946L;

    @ApiModelProperty(value = "userId")
    @NotBlank(message = "userId不能为空")
    private String userId;

    @ApiModelProperty(value = "删除标志(0正常 1停用)")
    @NotBlank(message = "删除标志不能为空")
    private String delFlag;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
