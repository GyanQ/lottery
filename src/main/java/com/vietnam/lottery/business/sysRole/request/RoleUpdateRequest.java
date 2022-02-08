package com.vietnam.lottery.business.sysRole.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class RoleUpdateRequest implements Serializable {
    private static final long serialVersionUID = -1677996909750150435L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "删除标志(0正常 1删除)")
    private String delFlag;

    @ApiModelProperty(hidden = true)
    private Long updateBy;

    @ApiModelProperty(hidden = true)
    private Date updateDate;
}
