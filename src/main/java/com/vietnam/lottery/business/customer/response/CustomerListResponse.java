package com.vietnam.lottery.business.customer.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerListResponse implements Serializable {
    private static final long serialVersionUID = -4596702099178344333L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "tele账号")
    private String teleAccount;
}
