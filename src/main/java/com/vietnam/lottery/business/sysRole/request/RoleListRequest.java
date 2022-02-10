package com.vietnam.lottery.business.sysRole.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class RoleListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -7371509006472383033L;

    @ApiModelProperty(value = "角色名称")
    private String name;
}

