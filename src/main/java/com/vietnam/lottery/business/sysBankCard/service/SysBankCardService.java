package com.vietnam.lottery.business.sysBankCard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;

/**
 * 用户银行信息(SysBankCard)表服务接口
 *
 * @author Gyan
 * @since 2022-04-01 10:49:51
 */
public interface SysBankCardService {

    //列表
    Page<SysBankCard> list(BankCardListRequest request);
}

