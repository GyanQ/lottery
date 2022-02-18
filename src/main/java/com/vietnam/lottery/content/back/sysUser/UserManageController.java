package com.vietnam.lottery.content.back.sysUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysUser.request.UserManageListRequest;
import com.vietnam.lottery.business.sysUser.response.UserManageListResponse;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
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
@Api(tags = "用户管理")
@RequestMapping("/sys/userManage")
public class UserManageController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/list")
    @ApiOperation("用户列表")
    public ResultModel<Page<UserManageListResponse>> list(@RequestBody UserManageListRequest request) {
        return ResultUtil.success(sysUserService.manageList(request));
    }
}
