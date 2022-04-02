package com.vietnam.lottery.business.sysBankCard.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankCardDeleteRequest implements Serializable {
    private static final long serialVersionUID = 3014161430709131367L;

    private String id;

    @ApiModelProperty(hidden = true)
    private String userId;
}
