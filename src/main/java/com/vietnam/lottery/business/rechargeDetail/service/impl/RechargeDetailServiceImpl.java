package com.vietnam.lottery.business.rechargeDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 充值记录(RechargeDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 11:48:03
 */
@Service("rechargeDetailService")
public class RechargeDetailServiceImpl implements RechargeDetailService {
    @Autowired
    private RechargeDetailMapper rechargeDetailMapper;

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<RechargeListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return rechargeDetailMapper.list(page, request);
    }
}

