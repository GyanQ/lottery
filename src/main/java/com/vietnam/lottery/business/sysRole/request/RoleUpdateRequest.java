package com.vietnam.lottery.business.sysRole.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RoleUpdateRequest implements Serializable {
    private static final long serialVersionUID = -1677996909750150435L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
