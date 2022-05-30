package com.vietnam.lottery.business.unpackRedPackets.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class BroadcastResponse implements Serializable {

    private static final long serialVersionUID = -4306825269462585984L;


    private String userId;

    @ApiModelProperty(value = "0拆到  1抢到")
    private String type;

    private BigDecimal amount;
}
