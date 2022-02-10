package com.vietnam.lottery.content.back.sysUser;

import com.vietnam.lottery.business.sysUser.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/sys/userManage")
public class UserManageController {
    @Autowired
    private SysUserService sysUserService;


}
