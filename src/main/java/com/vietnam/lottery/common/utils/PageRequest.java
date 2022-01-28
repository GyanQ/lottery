package com.vietnam.lottery.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageRequest {

    @ApiModelProperty(value = "当前页")
    private Long current;

    @ApiModelProperty(value = "页面数量")
    private Long size;
}
