package com.vietnam.lottery.business.sysBankCard.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankCardListResponse implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收款人姓名")
    private String collectionName;

    @ApiModelProperty(value = "银行卡号")
    private String cardNum;

    @ApiModelProperty(value = "银行编号")
    private String cardSerialNum;

    @ApiModelProperty(value = "银行名称")
    private String backName;
}
