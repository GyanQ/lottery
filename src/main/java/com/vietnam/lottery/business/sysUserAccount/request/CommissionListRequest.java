package com.vietnam.lottery.business.sysUserAccount.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommissionListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 7095749024710331773L;

    @ApiModelProperty(value = "手机、用户id、账号")
    private String keyWord;

    @ApiModelProperty(value = "排序 1用户充值升序  2用户充值降序 3分佣收益升序 4分佣收益降序")
    private String type;
}
