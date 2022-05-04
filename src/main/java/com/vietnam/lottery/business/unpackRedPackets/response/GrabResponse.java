package com.vietnam.lottery.business.unpackRedPackets.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GrabResponse implements Serializable {

    private static final long serialVersionUID = -4306825269462585984L;

    private String userId;

    private String type = "1";

    private BigDecimal amount;
}
