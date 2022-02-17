package com.vietnam.lottery.business.withdrawDetail.response;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WithdrawListResponse extends PageRequest implements Serializable {
    private static final long serialVersionUID = -781612067551178849L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现状态(1提现中 2已提现)")
    private String state;

    @ApiModelProperty(value = "提现时间")
    private String createDate;
}
