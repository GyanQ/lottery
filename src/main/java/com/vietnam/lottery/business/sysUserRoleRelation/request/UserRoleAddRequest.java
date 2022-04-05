package com.vietnam.lottery.business.sysUserRoleRelation.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserRoleAddRequest implements Serializable {
    private static final long serialVersionUID = -5264822246891070179L;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
