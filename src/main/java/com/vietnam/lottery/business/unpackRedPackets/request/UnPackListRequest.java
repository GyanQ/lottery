package com.vietnam.lottery.business.unpackRedPackets.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UnPackListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "抢红包id")
    private String grabId;
}
