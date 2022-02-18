package com.vietnam.lottery.business.sysUser.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysLoginDetail.mapper.SysLoginDetailMapper;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.response.*;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Autowired
    private SysLoginDetailMapper sysLoginDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(LoginRequest request) {
        //查询账号是否存在
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("登录失败,没有该账号信息");
        //校验密码
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("密码错误！");

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("管理后台登录");
        record.setOperate("登录");
        record.setContent("管理后台登录");
        String userId = JwtUtil.parseToken(token);
        record.setCreateBy(Long.valueOf(userId));
        sysOperateRecordService.add(record);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel register(UserRegisterRequest request) {
        Boolean flag = isExist(request.getAccount());
        if (!flag) return ResultUtil.failure("账号已存在");

        String passWord = DigestUtils.md5DigestAsHex(request.getPassWord().getBytes());
        SysUser user = new SysUser();
        user.setCreateBy(1l);
        user.setPhone(request.getPhone());
        user.setAccount(request.getAccount());
        user.setCreateDate(new Date());
        user.setPassWord(passWord);

        //todo:增加手机验证码逻辑
        return ResultUtil.success(sysUserMapper.insert(user));
    }

    @Override
    public Map<String, Object> frontLogin(LoginRequest request) {
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("登录失败,没有该账号信息");
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("密码错误！");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel updatePaw(UpdatePawRequest request) {
        SysUser user = sysUserMapper.selectById(request.getCreateBy());
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("查询不到用户信息");
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) return ResultUtil.failure("原密码输入错误！");

        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        sysUserMapper.updateById(user);

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("修改密码");
        record.setOperate("修改");
        record.setContent("修改密码");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return null;
    }

    @Override
    public UserGetPermissionResponse getPermission(Long id) {
        UserGetPermissionResponse response = sysUserMapper.selectRoleName(id);
        List<MenuPermissionResponse> menuPermission = sysUserMapper.selectMenuPermission(response.getRoleId());
        response.setList(menuPermission);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel createAccount(CreateAccountRequest request) {
        SysUser sysUser = accountIsExist(request.getAccount());
        if (!ObjectUtil.isEmpty(sysUser)) return ResultUtil.failure("账号已存在");

        Boolean flag = sysUserMapper.isSuperAdmin("超级管理员");
        if (!flag) return ResultUtil.failure("该账号没有创建账号权限");
        SysUser user = new SysUser();
        user.setAccount(request.getAccount());
        user.setName(request.getName());
        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("新增");
        record.setContent("创建账号");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysUserMapper.insert(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel resetPaw(ResetPawRequest request) {
        Boolean flag = sysUserMapper.isSuperAdmin("超级管理员");
        if (!flag) return ResultUtil.failure("该账号没有重置权限");
        SysUser user = new SysUser();
        user.setId(request.getUserId());
        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("修改");
        record.setContent("重置管理员密码");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysUserMapper.updateById(user));
    }

    @Override
    public Page<UserListResponse> list(UserListRequest request) {
        Page<UserListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserMapper.list(page, request);
    }

    @Override
    public UserDetailResponse detail(Long id) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("查询不到该用户信息");
        UserDetailResponse response = new UserDetailResponse();
        response.setName(user.getName());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(UserDeleteRequest request) {
        SysUser user = sysUserMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("账号不存在");
        user.setName(request.getName());
        user.setDelFlag(request.getDelFlag());
        user.setUpdateBy(request.getCreateBy());
        user.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("修改");
        record.setContent("修改后台账号");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysUserMapper.updateById(user));
    }

    @Override
    public Page<UserManageListResponse> manageList(UserManageListRequest request) {
        Page<UserManageListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        Page<UserManageListResponse> iPage = sysUserMapper.manageList(page, request);
        if (CollectionUtils.isEmpty(iPage.getRecords())) return iPage;

        iPage.getRecords().forEach(o -> {
            UserManageListResponse response = new UserManageListResponse();
            response.setEndDate(sysLoginDetailMapper.selectDate(o.getUserId()));
        });
        return iPage;
    }

    @Override
    public Page<GrabRedPacketsListResponse> grabRedPackets(GrabRedPacketsListRequest request) {
        Page<GrabRedPacketsListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserMapper.redPacketsList(page,request);
    }

    /* 账号是否唯一 */
    private Boolean isExist(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Integer count = sysUserMapper.selectCount(queryWrapper);
        if (0 < count) {
            return false;
        }
        return true;
    }

    /* 查询账号是否存在 */
    private SysUser accountIsExist(String account) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("account", account).eq("del_flag", DelFlagEnum.CODE.getCode()));
        return user;
    }

    /* 校验密码 */
    private Boolean checkPassWord(String first, String second) {
        return DigestUtils.md5DigestAsHex(first.getBytes()).equals(second);
    }
}
