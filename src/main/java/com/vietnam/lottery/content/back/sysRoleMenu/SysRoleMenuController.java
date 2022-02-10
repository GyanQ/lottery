package com.vietnam.lottery.content.back.sysRoleMenu;

import com.vietnam.lottery.business.sysRoleMenu.request.menuConfigRequest;
import com.vietnam.lottery.business.sysRoleMenu.service.SysRoleMenuService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "角色菜单配置")
@RequestMapping("/sys/roleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @PostMapping("/menuConfig")
    @ApiOperation("菜单配置")
    public ResultModel menuConfig(@RequestBody @Valid menuConfigRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        String userId = JwtUtil.parseToken(token);
        request.setUpdateBy(Long.valueOf(userId));
        return ResultUtil.success(sysRoleMenuService.menuConfig(request));
    }
}
