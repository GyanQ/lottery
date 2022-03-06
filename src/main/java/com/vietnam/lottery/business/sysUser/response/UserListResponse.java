package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class
UserListResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "所属角色")
    private String roleName;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "删除标志(0正常 1停用)")
    private String delFlag;
}
