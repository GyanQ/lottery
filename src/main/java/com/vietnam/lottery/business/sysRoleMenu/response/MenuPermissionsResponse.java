package com.vietnam.lottery.business.sysRoleMenu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuPermissionsResponse implements Serializable {
    private static final long serialVersionUID = 2110439453776921822L;

    @ApiModelProperty(value = "菜单id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String name;
}
