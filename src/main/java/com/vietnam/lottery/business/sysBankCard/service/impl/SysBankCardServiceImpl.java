package com.vietnam.lottery.business.sysBankCard.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.mapper.SysBankCardMapper;
import com.vietnam.lottery.business.sysBankCard.request.BankCardAddRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardDeleteRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardUpdateRequest;
import com.vietnam.lottery.business.sysBankCard.response.BankCardListResponse;
import com.vietnam.lottery.business.sysBankCard.service.SysBankCardService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private SysBankCardMapper sysBackCardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(BankCardAddRequest request) {
        SysBankCard sysBackCard = new SysBankCard();
        sysBackCard.setCardNum(request.getCardNum());
        sysBackCard.setBackName(request.getBackName());
        sysBackCard.setCardSerialNum(request.getCardSerialNum());
        sysBackCard.setCreateBy(request.getCreateBy());
        sysBackCard.setCollectionName(request.getCollectionName());
        sysBackCardMapper.insert(sysBackCard);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(BankCardUpdateRequest request) {
        SysBankCard bankCard = sysBackCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(bankCard)) return ResultUtil.failure("fail to edit");

        bankCard.setCardNum(request.getCardNum());
        bankCard.setBackName(request.getBackName());
        bankCard.setCardSerialNum(request.getCardSerialNum());
        bankCard.setUpdateBy(request.getUpdateBy());
        bankCard.setUpdateDate(new Date());
        bankCard.setCollectionName(request.getCollectionName());
        return ResultUtil.success(sysBackCardMapper.updateById(bankCard));
    }

    @Override
    public Page<BankCardListResponse> list(BankCardListRequest request) {
        Page<SysBankCard> page = new Page<>(request.getCurrent(), request.getSize());

        QueryWrapper<SysBankCard> query = new QueryWrapper<>();
        query.eq("create_by", request.getUserId()).eq("del_flag", DelFlagEnum.CODE.getCode());
        Page<SysBankCard> iPage = sysBackCardMapper.selectPage(page, query);

        Page<BankCardListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<BankCardListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        iPage.getRecords().forEach(o -> {
            BankCardListResponse response = new BankCardListResponse();
            response.setCardNum(o.getCardNum());
            response.setBackName(o.getBackName());
            response.setCardSerialNum(o.getCardSerialNum());
            response.setCollectionName(o.getCollectionName());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(BankCardDeleteRequest request) {
        SysBankCard bankCard = sysBackCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(bankCard)) return ResultUtil.failure("fail to delete");

        bankCard.setDelFlag(DelFlagEnum.MESSAGE.getCode());
        sysBackCardMapper.updateById(bankCard);
        return ResultUtil.success();
    }
}

