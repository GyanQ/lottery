package com.vietnam.lottery.content.front.customer;

import com.vietnam.lottery.business.customer.response.CustomerListResponse;
import com.vietnam.lottery.business.customer.service.CustomerService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "客服配置")
@RequestMapping("/web/customer")
public class CustomerWebController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ResultModel<CustomerListResponse> detail() {
        return ResultUtil.success(customerService.detail());
    }
}
