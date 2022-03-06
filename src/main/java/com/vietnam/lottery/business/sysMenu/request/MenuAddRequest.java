package com.vietnam.lottery.business.sysMenu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuAddRequest implements Serializable {

    private static final long serialVersionUID = -3311453420735204367L;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "顺序")
    @NotNull(message = "菜单顺序不能为空")
    private Integer sort;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
