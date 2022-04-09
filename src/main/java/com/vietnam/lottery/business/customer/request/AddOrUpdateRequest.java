package com.vietnam.lottery.business.customer.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddOrUpdateRequest implements Serializable {
    private static final long serialVersionUID = 828174882348351758L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "tele账号")
    private String teleAccount;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
