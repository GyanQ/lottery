package com.vietnam.lottery.business.unpackRedPackets.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UnPackDeleteRequest implements Serializable {
    private static final long serialVersionUID = 8471851668659581052L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private Long id;

    @ApiModelProperty(hidden = true)
    private Long deleteBy;
}
