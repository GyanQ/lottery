package com.vietnam.lottery.content.front.grabRedPackets;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.request.ListRequest;
import com.vietnam.lottery.business.grabRedPackets.response.ListResponse;
import com.vietnam.lottery.business.grabRedPackets.service.GrabRedPacketsService;
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
@Api(tags = "抢红包")
@RequestMapping("/web/grab")
public class GrabRedPacketsWebController {
    @Autowired
    private GrabRedPacketsService grabRedPacketsService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<ListResponse>> list(@RequestBody ListRequest request) {
        return ResultUtil.success(grabRedPacketsService.list(request));
    }

    @PostMapping("/bet")
    @ApiOperation("下注")
    public ResultModel bet(@RequestBody ListRequest request) {
        return ResultUtil.success(grabRedPacketsService.list(request));
    }
}
