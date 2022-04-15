package com.vietnam.lottery.content.back.sysMenu;

import com.vietnam.lottery.business.sysMenu.request.MenuAddRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuUpdateRequest;
import com.vietnam.lottery.business.sysMenu.response.MenuDetailResponse;
import com.vietnam.lottery.business.sysMenu.response.MenuLiseResponse;
import com.vietnam.lottery.business.sysMenu.service.SysMenuService;
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
import java.util.List;

@RestController
@Api(tags = "菜单配置")
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultModel add(@RequestBody @Valid MenuAddRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysMenuService.add(request));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultModel update(@RequestBody @Valid MenuUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setUpdateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(sysMenuService.update(request));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("详情")
    public ResultModel<MenuDetailResponse> detail(@PathVariable("id") Long id) {
        return ResultUtil.success(sysMenuService.detail(id));
    }

    @PostMapping("/list")
    @ApiOperation("列表")
    public ResultModel<List<MenuLiseResponse>> list() {
        return ResultUtil.success(sysMenuService.list());
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultModel delete(@RequestBody @Valid MenuDeleteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(sysMenuService.delete(request));
    }
}
