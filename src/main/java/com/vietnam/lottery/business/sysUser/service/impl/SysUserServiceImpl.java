package com.vietnam.lottery.business.sysUser.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUser.request.LoginRequest;
import com.vietnam.lottery.business.sysUser.request.UserRegisterRequest;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Map<String, Object> login(LoginRequest request) {
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("登录失败,没有该账号信息");
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("密码错误！");

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);
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

    /* 账号是否唯一*/
    private Boolean isExist(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Integer count = sysUserMapper.selectCount(queryWrapper);
        if (0 < count) {
            return false;
        }
        return true;
    }

    /* 查询账号是否存在*/
    private SysUser accountIsExist(String account) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("account", account).eq("del_flag", DelFlagEnum.CODE.getCode()));
        return user;
    }

    /* 校验密码 */
    private Boolean checkPassWord(String first, String second) {
        return DigestUtils.md5DigestAsHex(first.getBytes()).equals(second);
    }
}
