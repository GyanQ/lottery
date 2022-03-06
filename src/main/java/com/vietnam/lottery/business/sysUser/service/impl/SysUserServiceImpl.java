package com.vietnam.lottery.business.sysUser.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.actingCommissionDetail.mapper.ActingCommissionDetailMapper;
import com.vietnam.lottery.business.basicIndicators.entity.KeepStatistics;
import com.vietnam.lottery.business.basicIndicators.mapper.KeepStatisticsMapper;
import com.vietnam.lottery.business.lotteryDetail.mapper.LotteryDetailMapper;
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
import com.vietnam.lottery.business.sysUserRoleRelation.mapper.SysUserRoleRelationMapper;
import com.vietnam.lottery.business.withdrawDetail.mapper.WithdrawDetailMapper;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.config.SmsUtils;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
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
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Autowired
    private SysLoginDetailMapper sysLoginDetailMapper;
    @Autowired
    private ActingCommissionDetailMapper actingCommissionDetailMapper;
    @Autowired
    private WithdrawDetailMapper withdrawDetailMapper;
    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;
    @Autowired
    private SysSmsMapper sysSmsMapper;
    @Autowired
    private KeepStatisticsMapper keepStatisticsMapper;
    @Resource
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(LoginRequest request) {
        //查询账号是否存在
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("登录失败,没有该账号信息");
        user.setLoginWay("2");
        sysUserMapper.updateById(user);
        //校验密码
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("密码错误！");

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
        if (!flag) return ResultUtil.failure("账号已存在");

        String passWord = DigestUtils.md5DigestAsHex(request.getPassWord().getBytes());
        SysUser user = new SysUser();
        user.setAccount(request.getAccount());
        user.setPassWord(passWord);
        user.setLoginWay("1");
        user.setPhone(request.getPhone());

        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (!code.equals(request.getCode())) {
            return ResultUtil.failure("验证码错误");
        }
        return ResultUtil.success(sysUserMapper.insert(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> amountLogin(LoginRequest request) {
        //查询账号是否存在
        SysUser user = accountIsExist(request.getAccount());
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("登录失败,没有该账号信息");
        user.setLoginWay("2");
        sysUserMapper.updateById(user);
        //校验密码
        Boolean flag = checkPassWord(request.getPassWord(), user.getPassWord());
        if (!flag) throw new GlobalException("密码错误！");

        //推广


        //add留存记录
        Boolean keep = isKeep(user.getId());
        if (keep) {
            KeepStatistics statistics = new KeepStatistics();
            statistics.setCreateBy(user.getId());
            statistics.setRegisterDate(user.getCreateDate());
            keepStatisticsMapper.insert(statistics);
        }
        Map<String, Object> map = new HashMap<>();
        //创建token
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
        if (!ObjectUtil.isEmpty(sysUser)) return ResultUtil.failure("账号已存在");

        Boolean flag = sysUserMapper.isSuperAdmin("超级管理员");
        if (!flag) return ResultUtil.failure("该账号没有创建账号权限");
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
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("查询不到该账号,重置失败");

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
        if (ObjectUtil.isEmpty(sysUser)) return ResultUtil.failure("查询不到该用户");

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
        SysUser user = new SysUser();
        //查询账号是否存在
        user = accountIsExist(request.getUserId().toString());
        if (ObjectUtil.isEmpty(user)) {
            user.setLoginWay("2");
            user.setAccount(request.getUserId().toString());
            user.setCreateBy(request.getUserId());
            user.setName(request.getName());
            sysUserMapper.insert(user);
        }

        //add留存记录
        Boolean keep = isKeep(user.getId());
        if (keep) {
            KeepStatistics statistics = new KeepStatistics();
            statistics.setCreateBy(user.getId());
            statistics.setRegisterDate(user.getCreateDate());
            keepStatisticsMapper.insert(statistics);
        }

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", request.getUserId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);
        return map;
    }

    @Override
    public AccountBalanceResponse accountBalance(String userId) {
        AccountBalanceResponse resp = new AccountBalanceResponse();
        SysUser user = getById(userId);
        resp.setAmount(user.getAmount());
        //分佣余额
        CommissionAmountResponse commissionAmountResp = actingCommissionDetailMapper.commissionAmount(user.getId());
        //分佣提现余额
        Long commissionWithdraw = withdrawDetailMapper.commissionWithdraw(commissionAmountResp.getId(), null);
        Long commissionTotal = commissionAmountResp.getAmount() - (commissionWithdraw);
        resp.setCommissionBalanceAmount(commissionTotal);
        //红包余额
        LotteryAmountResponse lotteryAmountResp = lotteryDetailMapper.lotteryAmount(user.getId());
        Long lotteryWithdraw = withdrawDetailMapper.commissionWithdraw(null, lotteryAmountResp.getId());
        Long lotteryTotal = lotteryAmountResp.getAmount() - (lotteryWithdraw);
        resp.setRedEnvelopeAmount(lotteryTotal);
        return resp;
    }

    @Override
    public PromoteResponse promote(String id) {
        PromoteResponse resp = new PromoteResponse();
        resp.setUserId(id);
        //todo: 这里是项目登录地址
        resp.setUrl(null);
        return resp;
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
        if (ObjectUtil.isEmpty(user)) return ResultUtil.failure("账号不存在");
        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (!code.equals(request.getCode())) return ResultUtil.failure("验证码错误");

        user.setPassWord(DigestUtils.md5DigestAsHex(request.getPassWord().getBytes()));
        user.setUpdateDate(new Date());
        return ResultUtil.success(sysUserMapper.updateById(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> pawFreeLogin(PawFreeLoginRequest request) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("phone", request.getPhone()).eq("del_flag", DelFlagEnum.CODE.getCode()));
        if (ObjectUtil.isEmpty(user)) throw new GlobalException("账号不存在");

        String code = sysSmsMapper.selectByPhone(request.getPhone());
        if (!code.equals(request.getCode())) throw new GlobalException("验证码错误");

        //add留存记录
        Boolean keep = isKeep(user.getId());
        if (keep) {
            KeepStatistics statistics = new KeepStatistics();
            statistics.setCreateBy(user.getId());
            statistics.setRegisterDate(user.getCreateDate());
            keepStatisticsMapper.insert(statistics);
        }

        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        String token = JwtUtil.createToken(map);
        map.put("token", token);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> googleLogin(GoogleLoginRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel userRole(UserRoleRequest request) {

        return null;
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

    /* 查询当前时间是否有留存记录 */
    private Boolean isKeep(String userId) {
        Integer count = keepStatisticsMapper.isKeep(userId);
        if (0 < count) {
            return false;
        }
        return true;
    }
}
