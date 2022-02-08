package com.vietnam.lottery.business.sysMenu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysMenu.entity.SysMenu;
import com.vietnam.lottery.business.sysMenu.mapper.SysMenuMapper;
import com.vietnam.lottery.business.sysMenu.request.MenuAddRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuDeleteRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuListRequest;
import com.vietnam.lottery.business.sysMenu.request.MenuUpdateRequest;
import com.vietnam.lottery.business.sysMenu.response.MenuDetailResponse;
import com.vietnam.lottery.business.sysMenu.response.MenuLiseResponse;
import com.vietnam.lottery.business.sysMenu.service.SysMenuService;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单权限(SysMenu)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-08 10:48:13
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(MenuAddRequest request) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setName(request.getName());
        sysMenu.setSort(request.getSort());
        sysMenu.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysMenuMapper.insert(sysMenu));
    }

    @Override
    public Page<MenuLiseResponse> list(MenuListRequest request) {
        Page<SysMenu> page = new Page<>(request.getCurrent(), request.getSize());
        Page<SysMenu> iPage = sysMenuMapper.selectPage(page, new QueryWrapper<SysMenu>().orderByAsc("sort"));
        Page<MenuLiseResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return responsePage;
        }
        List<MenuLiseResponse> list = new ArrayList<>();
        iPage.getRecords().forEach(o -> {
            MenuLiseResponse response = new MenuLiseResponse();
            response.setId(o.getId());
            response.setName(o.getName());
            response.setSort(o.getSort());
            response.setDelFlag(o.getDelFlag());
            response.setCreateBy(o.getCreateBy());
            response.setCreateDate(o.getCreateDate());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(MenuUpdateRequest request) {
        SysMenu sysMenu = sysMenuMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(sysMenu)) {
            return ResultUtil.failure("该条数据不存在,无法修改!");
        }
        sysMenu.setName(request.getName());
        sysMenu.setSort(request.getSort());
        sysMenu.setDelFlag(request.getDelFlag());
        sysMenu.setUpdateBy(request.getUpdateBy());
        sysMenu.setUpdateDate(new Date());
        return ResultUtil.success(sysMenuMapper.updateById(sysMenu));
    }

    @Override
    public MenuDetailResponse detail(Long id) {
        SysMenu sysMenu = sysMenuMapper.selectOne(new QueryWrapper<SysMenu>().eq("id", id));
        if (ObjectUtil.isEmpty(sysMenu)) {
            throw new GlobalException("无法查询此数据！");
        }
        MenuDetailResponse response = new MenuDetailResponse();
        response.setId(sysMenu.getId());
        response.setName(sysMenu.getName());
        response.setSort(sysMenu.getSort());
        response.setDelFlag(sysMenu.getDelFlag());
        response.setCreateDate(sysMenu.getCreateDate());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(MenuDeleteRequest request) {
        return ResultUtil.success(sysMenuMapper.deleteById(request.getId()));
    }
}

