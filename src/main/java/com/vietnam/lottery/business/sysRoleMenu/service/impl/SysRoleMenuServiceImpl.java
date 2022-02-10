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
import java.util.List;
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
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return ResultUtil.success();
        }
        //菜单id
        List<Long> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        //需要新增的菜单
        List<Long> AddMenuIds = request.getMenuId().stream().filter(o -> !menuIds.contains(o)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(AddMenuIds)) {
            AddMenuIds.forEach(o -> {
                SysRoleMenu addMenu = new SysRoleMenu();
                addMenu.setRoleId(request.getRoleId());
                addMenu.setMenuId(o);
                addMenu.setCreateBy(request.getUpdateBy());
                sysRoleMenuMapper.insert(addMenu);
            });
        }

        //需要删除的菜单
        List<Long> deleteMenuIds = menuIds.stream().filter(o -> request.getMenuId().contains(o)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteMenuIds)) {
            AddMenuIds.forEach(o -> {
                SysRoleMenu deleteMenu = new SysRoleMenu();
                deleteMenu.setDelFlag(DelFlagEnum.MESSAGE.getCode());
                QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper();
                queryWrapper.eq("role_id", request.getRoleId()).eq("menu_id", o);
                sysRoleMenuMapper.update(deleteMenu, queryWrapper);
            });
        }

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("角色管理");
        record.setOperate("新增or修改");
        record.setContent("新增或者修改菜单配置");
        record.setCreateBy(request.getUpdateBy());
        return ResultUtil.success(sysOperateRecordService.add(record));
    }

    @Override
    public List<MenuPermissionsResponse> getByRoleMenuPermissions(Long roleId) {
        List<MenuPermissionResponse> list = sysUserMapper.selectMenuPermission(roleId);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<MenuPermissionsResponse> responsesList = new ArrayList<>();
        list.forEach(o -> {
            MenuPermissionsResponse response = new MenuPermissionsResponse();
            response.setId(o.getId());
            response.setName(o.getName());
            responsesList.add(response);
        });
        return responsesList;
    }
}

