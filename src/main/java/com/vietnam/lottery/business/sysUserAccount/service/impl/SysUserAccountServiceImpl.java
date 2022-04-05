package com.vietnam.lottery.business.sysUserAccount.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.business.sysUserAccount.request.UserLotteryListRequest;
import com.vietnam.lottery.business.sysUserAccount.response.UserLotteryListResponse;
import com.vietnam.lottery.business.sysUserAccount.service.SysUserAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 账户明细(SysUserAccount)表服务实现类
 *
 * @author Gyan
 * @since 2022-04-05 22:30:50
 */
@Service("sysUserAccountService")
public class SysUserAccountServiceImpl implements SysUserAccountService {
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;

    @Override
    public Page<UserLotteryListResponse> list(UserLotteryListRequest request) {
        Page<UserLotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserAccountMapper.list(page, request);
    }
}

