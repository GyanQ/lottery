package com.vietnam.lottery.content.front.recharge;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.recharge.request.RechargeListRequest;
import com.vietnam.lottery.business.recharge.response.RechargeListResponse;
import com.vietnam.lottery.business.recharge.service.RechargeService;
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
@Api(tags = "充值配置")
@RequestMapping("/web/recharge/config")
public class RechargeWebController {
    @Autowired
    private RechargeService rechargeService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<RechargeListResponse>> add(@RequestBody RechargeListRequest request) {
        return ResultUtil.success(rechargeService.list(request));
    }
}
