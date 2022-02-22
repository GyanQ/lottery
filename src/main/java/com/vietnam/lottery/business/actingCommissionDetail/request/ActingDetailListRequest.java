package com.vietnam.lottery.business.actingCommissionDetail.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActingDetailListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "排序 1用户充值升序  2用户充值降序 3分佣收益升序 4分佣收益降序")
    private String type;
}
