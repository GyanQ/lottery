package com.vietnam.lottery.business.sysUser.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GrabRedPacketsListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "userId")
    @NotNull(message = "userId不能为空")
    private Long userId;
}
