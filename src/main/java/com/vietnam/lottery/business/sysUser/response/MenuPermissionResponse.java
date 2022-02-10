package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuPermissionResponse implements Serializable {

    private static final long serialVersionUID = 3963546362419118946L;

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单顺序")
    private Integer sort;
}
