package com.vietnam.lottery.content.front.sysUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.request.CommissionDetailsRequest;
import com.vietnam.lottery.business.sysUserAccount.request.SubordinateListListRequest;
import com.vietnam.lottery.business.actingCommissionDetail.response.CommissionDetailsResponse;
import com.vietnam.lottery.business.sysUserAccount.response.SubordinateListListResponse;
import com.vietnam.lottery.business.actingCommissionDetail.service.ActingCommissionDetailService;
import com.vietnam.lottery.business.sysUser.response.AccountBalanceResponse;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "我的")
@RequestMapping("/sys/mine")
public class MineController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ActingCommissionDetailService actingCommissionDetailService;

    @PostMapping("/accountBalance")
    @ApiOperation("账户余额")
    public ResultModel<AccountBalanceResponse> accountBalance(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        return ResultUtil.success(sysUserService.accountBalance(userId));
    }

//    @PostMapping("/partner")
//    @ApiOperation("我的伙伴")
//    public ResultModel<List<SubordinateListListResponse>> partner(HttpServletRequest httpServletRequest) {
//        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
//        String userId = JwtUtil.parseToken(token);
//        SubordinateListListRequest listRequest = new SubordinateListListRequest();
//        listRequest.setUserId(userId);
//        return ResultUtil.success(actingCommissionDetailService.lowerLevelList(listRequest));
//    }

    @PostMapping("/commissionDetails")
    @ApiOperation("分佣明细")
    public ResultModel<Page<CommissionDetailsResponse>> commissionDetails(@RequestBody CommissionDetailsRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUserId(JwtUtil.parseToken(token));
        return ResultUtil.success(actingCommissionDetailService.commissionDetails(request));
    }
}
