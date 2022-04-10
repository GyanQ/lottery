package com.vietnam.lottery.business.sysBankCard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.mapper.SysBankCardMapper;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysBankCard.service.SysBankCardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户银行信息(SysBankCard)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-01 10:49:52
 */
@Service("sysBankCardService")
@Slf4j
public class SysBankCardServiceImpl implements SysBankCardService {
    @Resource
    private SysBankCardMapper sysBankCardMapper;

    @Override
    public Page<SysBankCard> list(BankCardListRequest request) {
        Page<SysBankCard> page = new Page<>(request.getCurrent(), request.getSize());

        QueryWrapper<SysBankCard> query = new QueryWrapper<>();
        query.like(!StringUtils.isBlank(request.getBankName()), "back_name", request.getBankName());
        return sysBankCardMapper.selectPage(page, query);
    }
}

