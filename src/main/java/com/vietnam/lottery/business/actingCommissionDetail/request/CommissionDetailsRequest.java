package com.vietnam.lottery.business.actingCommissionDetail.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommissionDetailsRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(hidden = true)
    private String userId;
}
