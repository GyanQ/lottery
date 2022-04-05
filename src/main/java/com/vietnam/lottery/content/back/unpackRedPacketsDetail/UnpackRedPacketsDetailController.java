package com.vietnam.lottery.content.back.unpackRedPacketsDetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.unpackRedPacketsDetail.service.UnpackRedPacketsDetailService;
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
@Api(tags = "拆红包开奖记录")
@RequestMapping("/lottery")
public class UnpackRedPacketsDetailController {
    @Autowired
    private UnpackRedPacketsDetailService unpackRedPacketsDetailService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<LotteryListResponse>> list(@RequestBody LotteryListRequest request) {
        return ResultUtil.success(unpackRedPacketsDetailService.list(request));
    }
}
