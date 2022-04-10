package com.vietnam.lottery.content.front.sysBankCard;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysBankCard.entity.SysBankCard;
import com.vietnam.lottery.business.sysBankCard.request.BankCardListRequest;
import com.vietnam.lottery.business.sysBankCard.service.SysBankCardService;
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
@Api(tags = "银行卡")
@RequestMapping("/web/back/crad")
public class SysBankCardWebController {
    @Autowired
    private SysBankCardService sysBankCardService;

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<SysBankCard>> add(@RequestBody BankCardListRequest request) {
        return ResultUtil.success(sysBankCardService.list(request));
    }
}
