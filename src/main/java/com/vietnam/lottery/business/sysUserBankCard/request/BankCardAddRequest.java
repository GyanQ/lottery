package com.vietnam.lottery.business.sysUserBankCard.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BankCardAddRequest implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    @ApiModelProperty(value = "银行列表id")
    @NotBlank(message = "银行列表id不能为空")
    private String id;

    @ApiModelProperty(value = "收款人姓名")
    @NotBlank(message = "收款人姓名不能为空")
    private String cardName;

    @ApiModelProperty(value = "收款人卡号")
    @NotBlank(message = "收款人卡号不能为空")
    private String cardNo;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
