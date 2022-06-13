package com.vietnam.lottery.business.sysUserBankCard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardAddRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardDeleteRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysUserBankCard.request.BankCardUpdateRequest;
import com.vietnam.lottery.business.sysUserBankCard.response.BankCardListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 用户银行卡详情(SysUserBankCard)表服务接口
 *
 * @author makejava
 * @since 2022-04-10 14:49:53
 */
public interface SysUserBankCardService {
    /* 新增 */
    ResultModel add(BankCardAddRequest request,String language);

    /* 修改*/
    ResultModel update(BankCardUpdateRequest request,String language);

    /* 列表 */
    Page<BankCardListResponse> list(BankCardListRequest request);

    /* 删除 */
    ResultModel delete(BankCardDeleteRequest request,String language);
}

