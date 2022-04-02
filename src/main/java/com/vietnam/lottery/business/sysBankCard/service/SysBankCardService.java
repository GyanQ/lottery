package com.vietnam.lottery.business.sysBankCard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.request.BankCardAddRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardDeleteRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysBankCard.request.BankCardUpdateRequest;
import com.vietnam.lottery.business.sysBankCard.response.BankCardListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 用户银行信息(SysBankCard)表服务接口
 *
 * @author Gyan
 * @since 2022-04-01 10:49:51
 */
public interface SysBankCardService {

    /* 新增 */
    ResultModel add(BankCardAddRequest request);

    /* 修改*/
    ResultModel update(BankCardUpdateRequest request);

    /* 列表 */
    Page<BankCardListResponse> list(BankCardListRequest request);

    /* 删除 */
    ResultModel delete(BankCardDeleteRequest request);
}

