package com.vietnam.lottery.business.sysRoleMenu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class menuConfigRequest implements Serializable {
    private static final long serialVersionUID = 2467293152037408332L;

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @ApiModelProperty(value = "菜单id")
    private List<String> menuId;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
