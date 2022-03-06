package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserGetPermissionResponse implements Serializable {

    private static final long serialVersionUID = -4719841891307326093L;

    @ApiModelProperty(hidden = true)
    private Long roleId;

    @ApiModelProperty(value = "菜单")
    List<MenuPermissionResponse> list = new ArrayList<>();
}
