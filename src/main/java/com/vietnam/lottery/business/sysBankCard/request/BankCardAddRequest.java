package com.vietnam.lottery.business.sysBankCard.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BankCardAddRequest implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    @ApiModelProperty(value = "收款人姓名")
    @NotBlank(message = "收款人姓名不能为空")
    private String collectionName;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String cardNum;

    @ApiModelProperty(value = "银行编号")
    @NotBlank(message = "银行编号不能为空")
    private String cardSerialNum;

    @ApiModelProperty(value = "银行名称")
    @NotBlank(message = "银行名称不能为空")
    private String backName;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
