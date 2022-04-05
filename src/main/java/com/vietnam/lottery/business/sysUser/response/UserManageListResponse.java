package com.vietnam.lottery.business.sysUser.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserManageListResponse implements Serializable {
    private static final long serialVersionUID = -9191474827254230835L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户余额")
    private Long amount;

    @ApiModelProperty(value = "累计充值")
    private Long rechargeTotal;

    @ApiModelProperty(value = "累计收益")
    private Long incomeTotal;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endDate;

    @ApiModelProperty(value = "登录类型")
    private String loginWay;

    @ApiModelProperty(value = "删除标志(0正常 1停用)")
    private String delFlag;

    @ApiModelProperty(value = "ip")
    private String ip;
}
