package com.vietnam.lottery.content.back.rechargeDetail;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
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
@Api(tags = "充值记录")
@RequestMapping("/recharge")
public class RechargeDetailController {
    @Autowired
    private RechargeDetailService rechargeDetailService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<RechargeListResponse>> list(@RequestBody RechargeListRequest request) {
        return ResultUtil.success(rechargeDetailService.list(request));
    }
}
