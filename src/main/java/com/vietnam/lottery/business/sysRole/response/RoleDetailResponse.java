package com.vietnam.lottery.business.sysRole.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RoleDetailResponse implements Serializable {
    private static final long serialVersionUID = -7371509006472383033L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}

