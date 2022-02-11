package com.vietnam.lottery.business.sysUser.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "删除标志(0正常 1删除)")
    private String delFlag;

    @ApiModelProperty(value = "昵称")
    private String name;
}
