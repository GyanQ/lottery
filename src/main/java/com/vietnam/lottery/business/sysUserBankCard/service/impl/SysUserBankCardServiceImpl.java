package com.vietnam.lottery.business.sysUserBankCard.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.mapper.SysBankCardMapper;
import com.vietnam.lottery.business.sysUserBankCard.entity.SysUserBankCard;
import com.vietnam.lottery.business.sysUserBankCard.mapper.SysUserBankCardMapper;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardAddRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardDeleteRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardUpdateRequest;
import com.vietnam.lottery.business.sysUserBankCard.response.BankCardListResponse;
import com.vietnam.lottery.business.sysUserBankCard.service.SysUserBankCardService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户银行卡详情(SysUserBankCard)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-10 14:49:53
 */
@Service("sysUserBankCardService")
public class SysUserBankCardServiceImpl implements SysUserBankCardService {
    @Resource
    private SysUserBankCardMapper sysUserBankCardMapper;
    @Resource
    private SysBankCardMapper sysBankCardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(BankCardAddRequest request) {
        SysBankCard sysBankCard = sysBankCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(sysBankCard)) return ResultUtil.failure("Không thể tìm thấy thông tin thẻ ngân hàng");

        SysUserBankCard userBankCard = new SysUserBankCard();
        userBankCard.setCardName(request.getCardName());
        userBankCard.setCardNo(request.getCardNo());
        userBankCard.setBankId(sysBankCard.getBankId());
        userBankCard.setBackName(sysBankCard.getBackName());
        userBankCard.setBankAbbreviation(sysBankCard.getBankAbbreviation());
        userBankCard.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysUserBankCardMapper.insert(userBankCard));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(BankCardUpdateRequest request) {
        SysBankCard sysBankCard = sysBankCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(sysBankCard)) return ResultUtil.failure("Không thể tìm thấy thông tin thẻ ngân hàng");


        SysUserBankCard userBankCard = sysUserBankCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(userBankCard)) return ResultUtil.failure("không thể chỉnh sửa");

        userBankCard.setCardName(request.getCardName());
        userBankCard.setCardNo(request.getCardNo());
        userBankCard.setBankId(sysBankCard.getBankId());
        userBankCard.setBackName(sysBankCard.getBackName());
        userBankCard.setBankAbbreviation(sysBankCard.getBankAbbreviation());
        userBankCard.setUpdateBy(request.getUpdateBy());
        userBankCard.setUpdateDate(new Date());
        return ResultUtil.success(sysUserBankCardMapper.updateById(userBankCard));
    }

    @Override
    public Page<BankCardListResponse> list(BankCardListRequest request) {
        Page<SysUserBankCard> page = new Page<>(request.getCurrent(), request.getSize());

        QueryWrapper<SysUserBankCard> query = new QueryWrapper<>();
        query.eq("create_by", request.getUserId());
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        Page<SysUserBankCard> iPage = sysUserBankCardMapper.selectPage(page, query);

        Page<BankCardListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<BankCardListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        iPage.getRecords().forEach(o -> {
            BankCardListResponse response = new BankCardListResponse();
            response.setId(o.getId());
            response.setCardName(o.getCardName());
            response.setCardNo(o.getCardNo());
            response.setBankId(o.getBankId());
            response.setBackName(o.getBackName());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(BankCardDeleteRequest request) {
        SysUserBankCard userBankCard = sysUserBankCardMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(userBankCard)) return ResultUtil.failure("không xóa được");

        userBankCard.setDelFlag(DelFlagEnum.MESSAGE.getCode());
        return ResultUtil.success(sysUserBankCardMapper.updateById(userBankCard));
    }
}

