package com.vietnam.lottery.business.sysRole.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RoleListResponse implements Serializable {
    private static final long serialVersionUID = -7371509006472383033L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "删除标志(0正常 1停用)")
    private String delFlag;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}

