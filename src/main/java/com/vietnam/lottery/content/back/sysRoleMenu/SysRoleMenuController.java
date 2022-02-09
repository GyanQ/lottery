package com.vietnam.lottery.content.back.sysRoleMenu;

import com.vietnam.lottery.business.sysRoleMenu.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "角色菜单配置")
@RequestMapping("/sys/roleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
}
