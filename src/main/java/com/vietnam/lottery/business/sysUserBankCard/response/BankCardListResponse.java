package com.vietnam.lottery.business.sysUserBankCard.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankCardListResponse implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收款人姓名")
    private String cardName;

    @ApiModelProperty(value = "银行卡号")
    private String cardNo;

    @ApiModelProperty(value = "银行编号")
    private String bankId;

    @ApiModelProperty(value = "银行名称")
    private String backName;
}
