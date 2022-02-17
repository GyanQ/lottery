package com.vietnam.lottery.business.withdrawDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.withdrawDetail.mapper.WithdrawDetailMapper;
import com.vietnam.lottery.business.withdrawDetail.request.WithdrawListRequest;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawDetailsResponse;
import com.vietnam.lottery.business.withdrawDetail.response.WithdrawListResponse;
import com.vietnam.lottery.business.withdrawDetail.service.WithdrawDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提现记录(WithdrawDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 10:50:09
 */
@Service("withdrawDetailService")
public class WithdrawDetailServiceImpl implements WithdrawDetailService {
    @Autowired
    private WithdrawDetailMapper withdrawDetailMapper;

    @Override
    public Page<WithdrawListResponse> list(WithdrawListRequest request) {
        Page<WithdrawListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return withdrawDetailMapper.list(page, request);
    }
}

