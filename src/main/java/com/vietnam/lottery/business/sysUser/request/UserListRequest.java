package com.vietnam.lottery.business.sysUser.request;

import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;
}
