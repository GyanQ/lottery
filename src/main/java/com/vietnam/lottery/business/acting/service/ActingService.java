package com.vietnam.lottery.business.acting.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.acting.request.ActingAddRequest;
import com.vietnam.lottery.business.acting.request.ActingDeleteRequest;
import com.vietnam.lottery.business.acting.request.ActingListRequest;
import com.vietnam.lottery.business.acting.request.ActingUpdateRequest;
import com.vietnam.lottery.business.acting.response.ActingDetailResponse;
import com.vietnam.lottery.business.acting.response.ActingListResponse;
import com.vietnam.lottery.common.utils.ResultModel;

/**
 * 代理配置(Acting)表服务接口
 *
 * @author Gyan
 * @since 2022-02-15 16:24:14
 */
public interface ActingService {

    /* 新增 */
    ResultModel add(ActingAddRequest request);

    /* 修改 */
    ResultModel update(ActingUpdateRequest request);

    /* 列表 */
    Page<ActingListResponse> list(ActingListRequest request);

    /* 删除 */
    ResultModel delete(ActingDeleteRequest request);

    /* 详情 */
    ActingDetailResponse detail(Long id);
}

