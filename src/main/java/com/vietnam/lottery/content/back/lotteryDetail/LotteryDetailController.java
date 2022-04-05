package com.vietnam.lottery.content.back.lotteryDetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.grabRedPacketsDetail.service.GrabRedPacketsDetailsService;
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
@Api(tags = "开奖记录")
@RequestMapping("/lottery")
public class LotteryDetailController {
    @Autowired
    private GrabRedPacketsDetailsService lotteryDetailService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<LotteryListResponse>> list(@RequestBody LotteryListRequest request) {
        return ResultUtil.success(lotteryDetailService.list(request));
    }
}
