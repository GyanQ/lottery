package com.vietnam.lottery.business.withdrawDetail.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -781612067551578849L;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endDate;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "审核状态(1未审核 2审核未通过 3审核通过)")
    private String audit;
}
