package com.vietnam.lottery.business.sysUserAccount.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WithdrawAuditRequest implements Serializable {
    private static final long serialVersionUID = -781612067551578849L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "审核状态(1未审核 2审核未通过 3审核通过)")
    @NotBlank(message = "审核状态不能为空")
    private String audit;

    @ApiModelProperty(hidden = true)
    private String createBy;
}
