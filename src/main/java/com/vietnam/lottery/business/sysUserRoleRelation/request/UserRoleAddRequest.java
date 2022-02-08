package com.vietnam.lottery.business.sysUserRoleRelation.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserRoleAddRequest implements Serializable {
    private static final long serialVersionUID = -5264822246891070179L;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
