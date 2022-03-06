package com.vietnam.lottery.business.withdrawDetail.response;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawListResponse extends PageRequest implements Serializable {
    private static final long serialVersionUID = -781612067551178849L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "提现金额")
    private Long amount;

    @ApiModelProperty(value = "提现状态(1提现中 2已提现)")
    private String state;

    @ApiModelProperty(value = "提现时间")
    private String createDate;

    @ApiModelProperty(value = "审核状态(1未审核 2审核未通过 3审核通过 4审核中)")
    private String audit;
}
