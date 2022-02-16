package com.vietnam.lottery.content.back.actingDetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingDetail.request.ActingDetailListRequest;
import com.vietnam.lottery.business.actingDetail.response.ActingDetailListResponse;
import com.vietnam.lottery.business.actingDetail.service.ActingDetailService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "代理详情")
@RequestMapping("/acting/detail")
public class ActingDetailController {
    @Autowired
    private ActingDetailService actingDetailService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<ActingDetailListResponse>> list(@RequestBody ActingDetailListRequest request) {
        return ResultUtil.success(actingDetailService.list(request));
    }
}
