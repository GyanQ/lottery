package com.vietnam.lottery.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 5489700435107066017L;

    @ApiModelProperty(value = "当前页")
    private Long current;

    @ApiModelProperty(value = "页面数量")
    private Long size;
}
