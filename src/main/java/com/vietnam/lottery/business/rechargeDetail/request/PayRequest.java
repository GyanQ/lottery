package com.vietnam.lottery.business.rechargeDetail.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PayRequest implements Serializable {
    private static final long serialVersionUID = -4915813726129006929L;

    @ApiModelProperty(value = "支付类型(1zalo 2momo)")
    @NotBlank(message = "支付类型不能为空")
    private String type;

    @ApiModelProperty(value = "充值金额id")
    @NotBlank(message = "充值金额id不能为空")
    private String id;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
