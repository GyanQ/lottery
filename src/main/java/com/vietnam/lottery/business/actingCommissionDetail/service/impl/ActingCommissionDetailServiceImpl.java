package com.vietnam.lottery.business.actingCommissionDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.mapper.ActingCommissionDetailMapper;
import com.vietnam.lottery.business.actingCommissionDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.request.LowerLevelListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.response.LowerLevelListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.service.ActingCommissionDetailService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
        return subList(request.getCurrent().intValue(), request.getSize().intValue(), list);
    }

    /* 递归查询下级代理用户 */
    private List<LowerLevelListResponse> myLowerLevel(String id) {
        List<LowerLevelListResponse> listResponses = actingCommissionDetailMapper.lowerLevelList(id);
        if (CollectionUtils.isEmpty(listResponses)) return list;
        for (LowerLevelListResponse o : listResponses) {
            if (id.equals(o.getLowerLevelId())) {
                //排除自己为其他人的下级
                continue;
            }
            list.addAll(myLowerLevel(o.getUserId()));
        }
        return list;
    }

    //subList手动分页，page为第几页，rows为每页个数
    public static <E> List<E> subList(Integer pageNumber, Integer pageSize, List<E> list) {
        if (CollectionUtils.isEmpty(list)) return Lists.newArrayList();
        int page = (pageNumber - 1) * pageSize;
        List<E> nowCollect = list.stream().skip(page).limit(pageSize).collect(toList());
        return Optional.ofNullable(nowCollect).orElse(Lists.newArrayList());
    }
}

