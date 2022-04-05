package com.vietnam.lottery.business.sysUserAccount.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLotteryListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -4915813726129006929L;

    @ApiModelProperty(value = "账号、手机号、userId")
    private String keyWord;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String endDate;
}
