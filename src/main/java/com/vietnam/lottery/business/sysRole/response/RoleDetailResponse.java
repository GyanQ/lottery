package com.vietnam.lottery.business.sysRole.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class RoleDetailResponse implements Serializable {
    private static final long serialVersionUID = -7371509006472383033L;

    @ApiModelProperty(value = "角色名称")
    private String name;
}

