package com.vietnam.lottery.business.sysUser.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vietnam.lottery.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserManageListRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endDate;

    @ApiModelProperty(value = "排序：1余额升序 2余额降序 3充值升序 4充值降序 5收益升序 6收益降序")
    private String type;
}
