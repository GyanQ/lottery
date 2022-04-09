package com.vietnam.lottery.business.customer.service.impl;

import cn.hutool.core.util.ObjectUtil;
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

import javax.annotation.Resource;
import java.util.Date;

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
    public CustomerListResponse detail() {
        Customer customer = customerMapper.selectOne(new QueryWrapper<>());

        CustomerListResponse response = new CustomerListResponse();
        if (ObjectUtil.isEmpty(customer)) return response;

        response.setId(customer.getId());
        response.setUrl(customer.getUrl());
        response.setTeleAccount(customer.getTeleAccount());
        return response;
    }
}

