package com.vietnam.lottery.content.back.sysUserRoleRelation;

import com.vietnam.lottery.business.sysUserRoleRelation.request.UserRoleAddRequest;
import com.vietnam.lottery.business.sysUserRoleRelation.service.SysUserRoleRelationService;
import com.vietnam.lottery.common.utils.JwtUtil;
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
@Api(tags = "角色管理")
@RequestMapping("/sys/userRole")
public class SysUserRoleRelationController {
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid UserRoleAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysUserRoleRelationService.add(request));
    }
}
