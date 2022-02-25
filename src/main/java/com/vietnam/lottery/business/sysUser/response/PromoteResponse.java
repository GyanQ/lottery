package com.vietnam.lottery.business.sysUser.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PromoteResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(hidden = true)
    private Long createBy;
}
