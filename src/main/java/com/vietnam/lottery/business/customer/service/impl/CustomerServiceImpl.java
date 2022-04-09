package com.vietnam.lottery.business.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vietnam.lottery.business.customer.entity.Customer;
import com.vietnam.lottery.business.customer.mapper.CustomerMapper;
import com.vietnam.lottery.business.customer.request.AddOrUpdateRequest;
import com.vietnam.lottery.business.customer.response.CustomerListResponse;
import com.vietnam.lottery.business.customer.service.CustomerService;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客服配置(Customer)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-07 23:31:38
 */
@Service("customerService")
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private SysOperateRecordService sysOperateRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel addOrUpdate(AddOrUpdateRequest request) {
        Customer customer = new Customer();
        if (StringUtils.isBlank(request.getId())) {
            customer.setUrl(request.getUrl());
            customer.setTeleAccount(request.getTeleAccount());
            customer.setCreateBy(request.getCreateBy());
            return ResultUtil.success(customerMapper.insert(customer));
        }
        customer.setId(request.getId());
        customer.setUrl(request.getUrl());
        customer.setTeleAccount(request.getTeleAccount());
        customer.setUpdateBy(request.getCreateBy());
        customer.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("客服配置");
        record.setOperate("新增or修改");
        record.setContent("新增or修改");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(customerMapper.updateById(customer));
    }

    @Override
    public List<CustomerListResponse> list() {
        List<Customer> list = customerMapper.selectList(new QueryWrapper<>());

        List<CustomerListResponse> responseList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) return responseList;

        list.forEach(o -> {
            CustomerListResponse resp = new CustomerListResponse();
            resp.setUrl(o.getUrl());
            resp.setTeleAccount(o.getTeleAccount());
            responseList.add(resp);
        });
        return responseList;
    }
}

