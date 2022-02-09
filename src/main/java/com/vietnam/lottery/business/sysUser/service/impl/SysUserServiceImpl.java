package com.vietnam.lottery.business.sysUser.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.response.MenuPermissionResponse;
import com.vietnam.lottery.business.sysUser.response.UserDetailResponse;
import com.vietnam.lottery.business.sysUser.response.UserGetPermissionResponse;
import com.vietnam.lottery.business.sysUser.response.UserListResponse;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.DateUtils;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;

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
    public ResultModel add() {
        return null;
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
        Boolean flag = sysUserMapper.isSuperAdmin("超级管理员");
        if (!flag) return ResultUtil.failure("该账号没有创建账号权限");
        SysUser user = new SysUser();
        user.setAccount(request.getAccount());
        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("新增");
        record.setContent("创建管理员账号：" + request.getAccount());
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
        Page<SysUser> page = new Page<>(request.getCurrent(), request.getSize());
        Page<SysUser> iPage = sysUserMapper.selectPage(page, new QueryWrapper<SysUser>().orderByDesc("create_date"));
        Page<UserListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getSize());
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;
        List<UserListResponse> list = new ArrayList<>();
        iPage.getRecords().forEach(o -> {
            UserListResponse resp = new UserListResponse();
            resp.setAccount(o.getAccount());
            resp.setId(o.getId());
            resp.setCreateDate(DateUtils.dateConversionStr(o.getCreateDate(), DateUtils.DATETIME_PATTERN));
            list.add(resp);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    public UserDetailResponse detail(String account) {
        SysUser user = accountIsExist(account);
        UserDetailResponse response = new UserDetailResponse();
        response.setAccount(user.getAccount());
        response.setPhone(user.getPhone());
        response.setCreateDate(DateUtils.dateConversionStr(user.getCreateDate(), DateUtils.DATETIME_PATTERN));
        return response;
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
