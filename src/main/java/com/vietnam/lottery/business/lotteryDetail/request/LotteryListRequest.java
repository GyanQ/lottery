package com.vietnam.lottery.business.lotteryDetail.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LotteryListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -4915813726129006929L;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endDate;
}
