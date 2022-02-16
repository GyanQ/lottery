package com.vietnam.lottery.business.grabRedPackets.request;

import com.vietnam.lottery.common.utils.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class ListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 6100185547611792135L;

}
