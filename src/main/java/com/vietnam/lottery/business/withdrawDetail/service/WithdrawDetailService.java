package com.vietnam.lottery.business.withdrawDetail.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawAuditRequest;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawListRequest;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 提现记录(WithdrawDetail)表服务接口
 *
 * @author Gyan
 * @since 2022-02-17 10:50:08
 */
public interface WithdrawDetailService {

    /* 提现列表 */
    Page<WithdrawListResponse> list(WithdrawListRequest request);

    /* 提现审核 */
    ResultModel audit(WithdrawAuditRequest request);
}

