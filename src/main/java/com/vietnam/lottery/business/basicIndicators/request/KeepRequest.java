package com.vietnam.lottery.business.basicIndicators.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KeepRequest implements Serializable {

    private static final long serialVersionUID = 1554835631571741239L;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String second;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String tree;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String server;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String fifteen;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private String moth;


    @ApiModelProperty(hidden = true)
    private String secondBegin;

    @ApiModelProperty(hidden = true)
    private String secondEnd;

    @ApiModelProperty(hidden = true)
    private String treeBegin;

    @ApiModelProperty(hidden = true)
    private String treeEnd;

    @ApiModelProperty(hidden = true)
    private String serverBegin;

    @ApiModelProperty(hidden = true)
    private String serverEnd;

    @ApiModelProperty(hidden = true)
    private String fifteenBegin;

    @ApiModelProperty(hidden = true)
    private String fifteenEnd;

    @ApiModelProperty(hidden = true)
    private String mothBegin;

    @ApiModelProperty(hidden = true)
    private String mothEnd;
}
