package com.vietnam.lottery.business.sysRoleMenu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysRoleMenu.entity.SysRoleMenu;
import com.vietnam.lottery.business.sysRoleMenu.mapper.SysRoleMenuMapper;
import com.vietnam.lottery.business.sysRoleMenu.request.menuConfigRequest;
import com.vietnam.lottery.business.sysRoleMenu.response.MenuPermissionsResponse;
import com.vietnam.lottery.business.sysRoleMenu.service.SysRoleMenuService;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUser.response.MenuPermissionResponse;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色菜单关系表(SysRoleMenu)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-08 17:18:53
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel menuConfig(menuConfigRequest request) {
        QueryWrapper<SysRoleMenu> query = new QueryWrapper<>();
        query.eq("role_id", request.getRoleId()).eq("del_flag", DelFlagEnum.CODE.getCode());
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(query);


        //如果没有菜单权限直接新增
        if (CollectionUtils.isEmpty(roleMenuList)) {
            SysRoleMenu menu = new SysRoleMenu();
            request.getMenuId().forEach(o -> {
                menu.setMenuId(o);
                menu.setRoleId(request.getRoleId());
                menu.setCreateBy(request.getUpdateBy());
                sysRoleMenuMapper.insert(menu);
            });
        }
        //删除全部菜单权限
        if (CollectionUtils.isEmpty(request.getMenuId()) && !CollectionUtils.isEmpty(roleMenuList)) {
            roleMenuList.forEach(o -> {
                SysRoleMenu deleteMenu = new SysRoleMenu();
                deleteMenu.setId(o.getId());
                deleteMenu.setDelFlag(DelFlagEnum.MESSAGE.getCode());
                sysRoleMenuMapper.updateById(deleteMenu);
            });
        }

        if (!CollectionUtils.isEmpty(roleMenuList)) {
            //单独提出角色所有权限的菜单id
            List<Long> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

            //删除菜单权限
            List<Long> deleteMenuLists = menuIds.stream().filter(o -> !request.getMenuId().contains(o)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(deleteMenuLists)) {
                deleteMenuLists.forEach(o -> {
                    QueryWrapper<SysRoleMenu> deleteWr = new QueryWrapper<>();
                    deleteWr.eq("role_id", request.getRoleId()).eq("menu_id", o).eq("del_flag", DelFlagEnum.CODE.getCode());
                    SysRoleMenu menu = new SysRoleMenu();
                    menu.setDelFlag(DelFlagEnum.MESSAGE.getCode());
                    menu.setUpdateBy(request.getUpdateBy());
                    menu.setUpdateDate(new Date());
                    sysRoleMenuMapper.update(menu, deleteWr);
                });

            }
            //新增菜单权限
            Set<Long> addMenuIds = request.getMenuId().stream().filter(o -> !menuIds.contains(o)).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(addMenuIds)) {
                addMenuIds.forEach(o -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(request.getRoleId());
                    roleMenu.setMenuId(o);
                    roleMenu.setCreateBy(request.getUpdateBy());
                    sysRoleMenuMapper.insert(roleMenu);
                });
            }
        }

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("菜单配置");
        record.setOperate("新增or修改");
        record.setContent("新增或者修改菜单配置");
        record.setCreateBy(request.getUpdateBy());
        return ResultUtil.success(sysOperateRecordService.add(record));
    }

    @Override
    public List<MenuPermissionsResponse> getByRoleMenuPermissions(Long roleId) {
        List<MenuPermissionResponse> list = sysUserMapper.selectMenuPermission(roleId);

        List<MenuPermissionsResponse> responsesList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return responsesList;
        }
        list.forEach(o -> {
            MenuPermissionsResponse response = new MenuPermissionsResponse();
            response.setId(o.getId());
            response.setName(o.getName());
            responsesList.add(response);
        });
        return responsesList;
    }
}

