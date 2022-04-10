package com.vietnam.lottery.business.sysUserBankCard.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankCardListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    @ApiModelProperty(hidden = true)
    private String userId;
}
