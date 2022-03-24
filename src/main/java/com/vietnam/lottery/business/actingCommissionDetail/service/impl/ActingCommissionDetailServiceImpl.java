package com.vietnam.lottery.business.actingCommissionDetail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.entity.ActingCommissionDetail;
import com.vietnam.lottery.business.actingCommissionDetail.mapper.ActingCommissionDetailMapper;
import com.vietnam.lottery.business.actingCommissionDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.CommissionDetailsRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.CommissionDetailsResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.LowerLevelListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.service.ActingCommissionDetailService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理详情(ActingCommissionDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 11:41:31
 */
@Service("actingDetailService")
public class ActingCommissionDetailServiceImpl implements ActingCommissionDetailService {
    @Resource
    private ActingCommissionDetailMapper actingCommissionDetailMapper;

    @Override
    public Page<ActingDetailListResponse> list(ActingDetailListRequest request) {
        Page<ActingDetailListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return actingCommissionDetailMapper.list(page, request);
    }

    //保存递归对象
    List<LowerLevelListResponse> list = new ArrayList<>();

    @Override
    public List<LowerLevelListResponse> lowerLevelList(LowerLevelListRequest request) {
        list = myLowerLevel(request.getUserId());
        return list;
    }

    @Override
    public Page<CommissionDetailsResponse> commissionDetails(CommissionDetailsRequest request) {
        Page<ActingCommissionDetail> page = new Page<>(request.getCurrent(), request.getSize());
        QueryWrapper<ActingCommissionDetail> query = new QueryWrapper<>();
        query.eq("del_flag", DelFlagEnum.CODE.getCode()).eq("create_by", request.getUserId());
        Page<ActingCommissionDetail> iPage = actingCommissionDetailMapper.selectPage(page, query);

        Page<CommissionDetailsResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<CommissionDetailsResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;

        Long total = 0l;
        for (ActingCommissionDetail o : iPage.getRecords()) {
            CommissionDetailsResponse resp = new CommissionDetailsResponse();
            resp.setDate(DateUtils.dateConversionStr(o.getCreateDate(), DateUtils.DATETIME_PATTERN));
            resp.setAmount(o.getAmount());
            total += o.getAmount();
            list.add(resp);
        }
        list.get(0).setTotal(total);
        responsePage.setRecords(list);
        return responsePage;
    }

    /* 递归查询下级代理用户 */
    private List<LowerLevelListResponse> myLowerLevel(String id) {
        List<LowerLevelListResponse> listResponses = actingCommissionDetailMapper.lowerLevelList(id);
        if (CollectionUtils.isEmpty(listResponses)) return list;
        list.addAll(listResponses);
        for (LowerLevelListResponse o : listResponses) {
            myLowerLevel(o.getUserId());
        }
        return list;
    }
}

