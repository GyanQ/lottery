package com.vietnam.lottery.content.back.sysRole;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysRole.request.RoleAddRequest;
import com.vietnam.lottery.business.sysRole.request.RoleListRequest;
import com.vietnam.lottery.business.sysRole.request.RoleUpdateRequest;
import com.vietnam.lottery.business.sysRole.response.RoleDetailResponse;
import com.vietnam.lottery.business.sysRole.response.RoleListResponse;
import com.vietnam.lottery.business.sysRole.service.SysRoleService;
import com.vietnam.lottery.common.utils.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "角色管理")
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid RoleAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysRoleService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel update(@RequestBody @Valid RoleUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysRoleService.update(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("详情")
    public ResultModel<RoleDetailResponse> detail(@PathVariable("id") Long id) {
        return ResultUtil.success(sysRoleService.detail(id));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<Page<RoleListResponse>> list(@RequestBody RoleListRequest request) {
        return ResultUtil.success(sysRoleService.list(request));
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid MenuDeleteRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysRoleService.delete(request));
    }
}
