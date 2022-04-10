package com.vietnam.lottery.business.sysBankCard.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankCardListRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -1695537181089400752L;

    @ApiModelProperty(value = "银行名称")
    private String bankName;
}
