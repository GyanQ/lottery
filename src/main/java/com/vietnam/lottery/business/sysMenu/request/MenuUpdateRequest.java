package com.vietnam.lottery.business.sysMenu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuUpdateRequest implements Serializable {
    private static final long serialVersionUID = -3372296366113251296L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "顺序")
    private Integer sort;

    @ApiModelProperty(value = "删除标志(0正常 1删除)")
    private String delFlag;

    @ApiModelProperty(hidden = true)
    private String updateBy;
}
