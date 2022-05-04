package com.vietnam.lottery.business.unpackRedPackets.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RandomResponse implements Serializable {

    private static final long serialVersionUID = -4306825269462585984L;

    private String userId;

    private String type = "0";

    private BigDecimal amount;
}
