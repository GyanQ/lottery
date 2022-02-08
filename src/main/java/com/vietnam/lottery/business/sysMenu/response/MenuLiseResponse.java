package com.vietnam.lottery.business.sysMenu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MenuLiseResponse implements Serializable {

    private static final long serialVersionUID = 4687781214153647626L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "顺序")
    private Integer sort;

    @ApiModelProperty(value = "删除标志(0正常 1删除)")
    private String delFlag;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}