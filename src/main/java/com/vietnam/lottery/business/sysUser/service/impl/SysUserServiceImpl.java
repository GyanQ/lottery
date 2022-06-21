package com.vietnam.lottery.business.sysUser.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.acting.entity.Acting;
import com.vietnam.lottery.business.acting.mapper.ActingMapper;
import com.vietnam.lottery.business.actingHierarchyRelation.entity.ActingHierarchyRelation;
import com.vietnam.lottery.business.actingHierarchyRelation.mapper.ActingHierarchyRelationMapper;
import com.vietnam.lottery.business.sysLoginDetail.entity.SysLoginDetail;
import com.vietnam.lottery.business.sysLoginDetail.mapper.SysLoginDetailMapper;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysSms.entity.SysSms;
import com.vietnam.lottery.business.sysSms.mapper.SysSmsMapper;
import com.vietnam.lottery.business.sysUser.entity.SysUser;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.business.sysUser.request.*;
import com.vietnam.lottery.business.sysUser.response.*;
import com.vietnam.lottery.business.sysUser.service.SysUserService;
import com.vietnam.lottery.business.sysUserRoleRelation.entity.SysUserRoleRelation;
import com.vietnam.lottery.business.sysUserRoleRelation.mapper.SysUserRoleRelationMapper;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Autowired
    private SysLoginDetailMapper sysLoginDetailMapper;
    @Autowired
    private SysSmsMapper sysSmsMapper;
    @Resource
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;
    @Resource
    private ActingHierarchyRelationMapper actingHierarchyRelationMapper;
    @Resource
    private ActingMapper actingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(LoginRequest request) {
        //查询账号是否存在
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("Unable to query user information");
        user.setLoginWay("1");
        sysUserMapper.updateById(user);
        //校验密码
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("wrong password");

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);
        map.put("account", user.getAccount());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("首页登录");
        record.setOperate("登录");
        record.setContent("管理后台登录");
        String userId = JwtUtil.parseToken(token);
        record.setCreateBy(userId);
        sysOperateRecordService.add(record);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel register(UserRegisterRequest request) {
        Boolean flag = isExist(request.getAccount());
        if (!flag) {
            if ("0".equals(request.getType())) {
                return ResultUtil.failure("Duplicate account");
            } else {
                return ResultUtil.failure("Tài khoản trùng lặp");
            }
        }

        String passWord = DigestUtils.md5DigestAsHex(request.getPassWord().getBytes());
        SysUser user = new SysUser();
        user.setAccount(request.getAccount());
        user.setPassWord(passWord);
        user.setLoginWay("1");
        user.setPhone(request.getPhone());

        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (StringUtils.isBlank(code)) {
            if ("0".equals(request.getType())) {
                return ResultUtil.failure("OTP incorrect");
            } else {
                return ResultUtil.failure("Mã xác nhận sai");
            }
        }
        if (!code.equals(request.getCode())) {
            if ("0".equals(request.getType())) {
                return ResultUtil.failure("OTP incorrect");
            } else {
                return ResultUtil.failure("Mã xác nhận sai");
            }
        }
        sysUserMapper.insert(user);
        //推广代理
        if (!StringUtils.isBlank(request.getUserId())) {
            addActing(request.getUserId(), user.getId());
        }
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> amountLogin(LoginRequest request) {
        //查询账号是否存在
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) {
            if ("0".equals(request.getType())) {
                throw new GlobalException("Account doesn't exist");
            } else {
                throw new GlobalException("Người dùng không tồn tại");
            }
        }
        user.setLoginWay("2");
        sysUserMapper.updateById(user);
        //校验密码
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) {
            if ("0".equals(request.getType())) {
                throw new GlobalException("Mật khẩu không chính xác");
            } else {
                throw new GlobalException("Password incorrect");
            }
        }

        Map<String, Object> map = new HashMap<>();
        //创建token
        map.put("userId", user.getId());
        map.put("language", request.getType());
        String token = JwtUtil.createToken(map);
        map.put("token", token);

        addLoginDetail(user.getId(), request.getIp());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel updatePaw(UpdatePawRequest request) {
        SysUser user = sysUserMapper.selectById(request.getCreateBy());
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("Unable to query user information");
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) return ResultUtil.failure("The original password was entered incorrectly");

        user.setPassWord(DigestUtils.md5DigestAsHex(request.getNewPassWord().getBytes()));
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
    public UserGetPermissionResponse getPermission(String id) {
        UserGetPermissionResponse response = sysUserMapper.selectRoleName(id);
        if (!ObjectUtil.isEmpty(response)) {
            List<MenuPermissionResponse> menuPermission = sysUserMapper.selectMenuPermission(response.getRoleId());
            if (!CollectionUtils.isEmpty(menuPermission)) {
                response.setList(menuPermission);
            }
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel createAccount(CreateAccountRequest request) {
        SysUser sysUser = accountIsExist(request.getAccount());
        if (!ObjectUtil.isEmpty(sysUser)) return ResultUtil.failure("account already exists");

        Boolean flag = sysUserMapper.isSuperAdmin("超级管理员");
        if (!flag) return ResultUtil.failure("This account does not have permission to create an account");
        SysUser user = new SysUser();
        user.setAccount(request.getAccount());
        user.setName(request.getName());
        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setCreateBy(request.getCreateBy());
        user.setLoginWay("1");

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
        SysUser user = sysUserMapper.selectById(request.getUserId());
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("reset failed");

        user.setPassWord(DigestUtils.md5DigestAsHex("123456".getBytes()));
        user.setUpdateBy(request.getCreateBy());
        user.setUpdateDate(new Date());

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
    public UserDetailResponse detail(String id) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("Unable to query user information");
        UserDetailResponse response = new UserDetailResponse();
        response.setName(user.getName());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(UserDeleteRequest request) {
        SysUser user = sysUserMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("Account does not exist");
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
        for (UserManageListResponse o : iPage.getRecords()) {
            SysLoginDetail loginInfo = sysUserMapper.loginInfo(o.getUserId());
            if (ObjectUtil.isEmpty(loginInfo)) continue;
            o.setEndDate(DateUtils.dateConversionStr(loginInfo.getCreateDate(), DateUtils.DATETIME_PATTERN));
            o.setIp(loginInfo.getIp());
        }
        return iPage;
    }

    @Override
    public Page<GrabRedPacketsListResponse> grabRedPackets(GrabRedPacketsListRequest request) {
        Page<GrabRedPacketsListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return sysUserMapper.redPacketsList(page, request);
    }

    @Override
    public UserDetailResponse userDetail(String id) {
        return sysUserMapper.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel pullBlack(PullBlackRequest request) {
        SysUser sysUser = sysUserMapper.selectById(request.getUserId());
        if (ObjectUtil.isEmpty(sysUser)) return ResultUtil.failure("Unable to query user information");

        sysUser.setDelFlag(request.getDelFlag());
        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("用户管理");
        record.setOperate("拉黑");
        record.setContent("新增或修改用户");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(sysUserMapper.updateById(sysUser));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> faceBookLogin(FaceBookLoginRequest request) {
        log.info("facebook入参:{}", request);
        //查询账号是否存在
        SysUser user = accountIsExist(request.getUserId().toString());
        if (ObjectUtil.isEmpty(user)) {
            user = new SysUser();
            user.setLoginWay("2");
            user.setAccount(request.getUserId().toString());
            user.setCreateBy(request.getUserId());
            user.setName(request.getName());
            sysUserMapper.insert(user);
        }

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", request.getUserId());
        map.put("language", request.getType());
        String token = JwtUtil.createToken(map);
        map.put("token", token);

        if (!StringUtils.isBlank(request.getId())) {
            addActing(request.getId(), user.getId());
        }
        addLoginDetail(user.getId(), request.getIp());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel sendSms(SendSmsRequest request) {
        //随机生成6位验证码
        String code = SmsUtils.code();
        SysSms sysSms = new SysSms();
        sysSms.setPhone(request.getPhone());
        sysSms.setCode(code);
        sysSms.setCreateBy(request.getCreateBy());
        sysSmsMapper.insert(sysSms);
        //发送短信验证码
        SmsUtils.send(request.getPhone(), code);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel retrievePaw(retrievePwdRequest request) {
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) {
            if ("0".equals(request.getType())) {
                throw new GlobalException("Account doesn't exist");
            } else {
                throw new GlobalException("Người dùng không tồn tại");
            }
        }
        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (!code.equals(request.getCode())) {
            if ("0".equals(request.getType())) {
                throw new GlobalException("OTP incorrect");
            } else {
                throw new GlobalException("Mã xác nhận sai");
            }
        }

        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setUpdateDate(new Date());
        return ResultUtil.success(sysUserMapper.updateById(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> pawFreeLogin(PawFreeLoginRequest request) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("phone", request.getPhone()).eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (ObjectUtil.isEmpty(user)) {
            user = new SysUser();
            String uuid = IdUtil.simpleUUID();
            user.setId(uuid);
            user.setPhone(request.getPhone());
            user.setLoginWay("4");
            user.setAccount(request.getPhone());
            user.setName(request.getPhone());
            sysUserMapper.insert(user);
        }

        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (!code.equals(request.getCode())) {
            if ("0".equals(request.getType())) {
                throw new GlobalException("OTP incorrect");
            } else {
                throw new GlobalException("Mã xác nhận sai");
            }

        }

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);

        if (!StringUtils.isBlank(request.getUserId())) {
            addActing(request.getUserId(), user.getId());
        }
        addLoginDetail(user.getId(), request.getIp());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> googleLogin(GoogleLoginRequest request) {
        log.info("Google入参:{}", request);
        //查询账号是否存在
        SysUser user = accountIsExist(request.getUserId().toString());
        if (ObjectUtil.isEmpty(user)) {
            user = new SysUser();
            user.setLoginWay("2");
            user.setAccount(request.getUserId().toString());
            user.setCreateBy(request.getUserId());
            user.setName(request.getName());
            sysUserMapper.insert(user);
        }

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", request.getUserId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);

        if (!StringUtils.isBlank(request.getId())) {
            addActing(request.getId(), user.getId());
        }
        addLoginDetail(user.getId(), request.getIp());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel userRole(UserRoleRequest request) {
        SysUserRoleRelation userRole = sysUserRoleRelationMapper.selectOne(new QueryWrapper<SysUserRoleRelation>().eq("user_id", request.getUserId()).eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (ObjectUtil.isEmpty(userRole)) {
            SysUserRoleRelation userRoleRelation = new SysUserRoleRelation();
            userRoleRelation.setRoleId(request.getRoleId());
            userRoleRelation.setUserId(request.getUserId());
            userRoleRelation.setCreateBy(request.getCreateBy());
            sysUserRoleRelationMapper.insert(userRoleRelation);
        } else {
            userRole.setRoleId(request.getRoleId());
            userRole.setUpdateBy(request.getCreateBy());
            userRole.setUpdateDate(new Date());
            sysUserRoleRelationMapper.updateById(userRole);
        }
        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("账户管理");
        record.setOperate("角色配置");
        record.setContent("修改");
        record.setCreateBy(request.getCreateBy());
        return ResultUtil.success(sysOperateRecordService.add(record));
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

    /* 根据userId查询用户信息 */
    private SysUser getById(String userId) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", userId).eq("del_flag", DelFlagEnum.CODE.getCode()));
        return user;
    }

    /**
     * 推广 (每个人最多挂三级)
     * superiorId: 父级userId
     * userid: 下级
     */
    @Transactional
    public void addActing(String superiorId, String userId) {
        //========增加代理关系
        ActingHierarchyRelation relation = new ActingHierarchyRelation();
        Acting selectActing = selectActingId("1501592852484911106", null);
        if (ObjectUtil.isEmpty(selectActing)) {
            return;
        }
        relation.setActingId(selectActing.getId());
        relation.setCreateBy(userId);
        relation.setSuperiorId(superiorId);
        actingHierarchyRelationMapper.insert(relation);

        //查询父级的上级代理
        QueryWrapper<ActingHierarchyRelation> querySuper = new QueryWrapper<>();
        querySuper.eq("create_by", superiorId);
        querySuper.eq("del_flag", DelFlagEnum.CODE.getCode());
        List<ActingHierarchyRelation> list = actingHierarchyRelationMapper.selectList(querySuper);
        if (CollectionUtils.isEmpty(list)) return;

        //二级、三级代理
        for (ActingHierarchyRelation o : list) {
            ActingHierarchyRelation byActingUserId = new ActingHierarchyRelation();

            switch (o.getActingId()) {
                //一级
                case "1501592852484911106":
                    byActingUserId.setActingId("1502622021805215745");
                    byActingUserId.setCreateBy(userId);
                    byActingUserId.setSuperiorId(o.getSuperiorId());
                    actingHierarchyRelationMapper.insert(byActingUserId);
                    break;
                //二级
                case "1502622021805215745":
                    byActingUserId.setActingId("1502622043628179459");
                    byActingUserId.setCreateBy(userId);
                    byActingUserId.setSuperiorId(o.getSuperiorId());
                    actingHierarchyRelationMapper.insert(byActingUserId);
                    break;
            }
        }
    }

    /* 查询代理等级 */
    private Acting selectActingId(String id, String level) {
        QueryWrapper<Acting> query = new QueryWrapper();
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        query.eq(null != level, "level", level);
        query.eq(null != id, "id", id);
        return actingMapper.selectOne(query);
    }

    //新增用户登录明细
    private void addLoginDetail(String userId, String ip) {
        SysLoginDetail login = new SysLoginDetail();
        login.setCreateBy(userId);
        login.setTemporaryUser("1");
        login.setIp(ip);
        sysLoginDetailMapper.insert(login);
    }
}
