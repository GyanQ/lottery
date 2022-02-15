package com.vietnam.lottery.business.acting.request;

import com.vietnam.lottery.common.utils.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActingListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 5715344767592473272L;
}
