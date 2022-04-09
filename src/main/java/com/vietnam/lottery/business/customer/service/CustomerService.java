package com.vietnam.lottery.business.customer.service;

import com.vietnam.lottery.business.customer.request.AddOrUpdateRequest;
import com.vietnam.lottery.business.customer.response.CustomerListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

import java.util.List;

/**
 * 客服配置(Customer)表服务接口
 *
 * @author Gyan
 * @since 2022-04-07 23:31:37
 */
public interface CustomerService {

    //新增and修改
    ResultModel addOrUpdate(AddOrUpdateRequest request);

    //新增and修改
    List<CustomerListResponse> list();
}

