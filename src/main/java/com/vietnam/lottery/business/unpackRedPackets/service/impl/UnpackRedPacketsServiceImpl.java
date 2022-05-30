package com.vietnam.lottery.business.unpackRedPackets.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.acting.entity.Acting;
import com.vietnam.lottery.business.acting.mapper.ActingMapper;
import com.vietnam.lottery.business.actingHierarchyRelation.entity.ActingHierarchyRelation;
import com.vietnam.lottery.business.actingHierarchyRelation.mapper.ActingHierarchyRelationMapper;
import com.vietnam.lottery.business.sysBroadcastConfig.mapper.SysBroadcastConfigMapper;
import com.vietnam.lottery.business.sysOperateRecord.entity.SysOperateRecord;
import com.vietnam.lottery.business.sysOperateRecord.service.SysOperateRecordService;
import com.vietnam.lottery.business.sysUserAccount.entity.SysUserAccount;
import com.vietnam.lottery.business.sysUserAccount.mapper.SysUserAccountMapper;
import com.vietnam.lottery.business.unpackRedPackets.entity.UnpackRedPackets;
import com.vietnam.lottery.business.unpackRedPackets.mapper.UnpackRedPacketsMapper;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackAddRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackDeleteRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackListRequest;
import com.vietnam.lottery.business.unpackRedPackets.request.UnPackUpdateRequest;
import com.vietnam.lottery.business.unpackRedPackets.response.*;
import com.vietnam.lottery.business.unpackRedPackets.service.UnpackRedPacketsService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 拆红包(UnpackRedPackets)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-16 18:00:37
 */
@Service("unpackRedPacketsService")
public class UnpackRedPacketsServiceImpl implements UnpackRedPacketsService {
    @Autowired
    private UnpackRedPacketsMapper unpackRedPacketsMapper;
    @Autowired
    private SysOperateRecordService sysOperateRecordService;
    @Resource
    private SysUserAccountMapper sysUserAccountMapper;
    @Resource
    private SysBroadcastConfigMapper sysBroadcastConfigMapper;
    @Resource
    private ActingHierarchyRelationMapper actingHierarchyRelationMapper;
    @Resource
    private ActingMapper actingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(UnPackAddRequest request) {
        List<UnpackRedPackets> unpackList = unpackList(null);
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = probability.add(request.getProbability());
            if (total.compareTo(new BigDecimal(100)) == 1) {
                return ResultUtil.failure("拆红包概率大于100%");
            }
        }
        UnpackRedPackets unpackRedPackets = new UnpackRedPackets();
        unpackRedPackets.setName(request.getName());
        unpackRedPackets.setIntervalBeginValue(request.getBegin());
        unpackRedPackets.setIntervalEndValue(request.getEnd());
        unpackRedPackets.setProbability(request.getProbability());
        unpackRedPackets.setCreateBy(request.getCreateBy());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("新增");
        record.setContent("新增拆红包配置");
        record.setCreateBy(request.getCreateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.insert(unpackRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel update(UnPackUpdateRequest request) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(unpackRedPackets)) return ResultUtil.failure("fail to edit");

        List<UnpackRedPackets> unpackList = unpackList(unpackRedPackets.getId());
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = probability.add(request.getProbability());
            if (total.compareTo(new BigDecimal(100)) == 1) {
                return ResultUtil.failure("拆红包概率大于100%");
            }
        }

        unpackRedPackets.setName(request.getName());
        unpackRedPackets.setIntervalBeginValue(request.getBegin());
        unpackRedPackets.setIntervalEndValue(request.getEnd());
        unpackRedPackets.setProbability(request.getProbability());
        unpackRedPackets.setUpdateBy(request.getUpdateBy());
        unpackRedPackets.setUpdateDate(new Date());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("修改");
        record.setContent("修改拆红包配置");
        record.setCreateBy(request.getUpdateBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.updateById(unpackRedPackets));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel delete(UnPackDeleteRequest request) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectById(request.getId());
        if (ObjectUtil.isEmpty(unpackRedPackets)) return ResultUtil.failure("fail to delete");
        unpackRedPackets.setDelFlag(DelFlagEnum.MESSAGE.getCode());

        //操作记录
        SysOperateRecord record = new SysOperateRecord();
        record.setModule("拆红包配置");
        record.setOperate("删除");
        record.setContent("删除拆红包配置");
        record.setCreateBy(request.getDeleteBy());
        sysOperateRecordService.add(record);
        return ResultUtil.success(unpackRedPacketsMapper.updateById(unpackRedPackets));
    }

    @Override
    public UnPackDetailResponse detail(Long id) {
        UnpackRedPackets unpackRedPackets = unpackRedPacketsMapper.selectOne(new QueryWrapper<UnpackRedPackets>().eq("id", id).eq("del_flag", DelFlagEnum.CODE.getCode()));
        UnPackDetailResponse response = new UnPackDetailResponse();
        if (ObjectUtil.isEmpty(unpackRedPackets)) return response;

        response.setBegin(unpackRedPackets.getIntervalBeginValue());
        response.setEnd(unpackRedPackets.getIntervalEndValue());
        response.setProbability(unpackRedPackets.getProbability());
        response.setName(unpackRedPackets.getName());
        return response;
    }

    @Override
    public Page<UnPackListResponse> list(UnPackListRequest request) {
        Page<UnpackRedPackets> page = new Page<>(request.getCurrent(), request.getSize());
        Page<UnpackRedPackets> iPage = unpackRedPacketsMapper.selectPage(page, new QueryWrapper<UnpackRedPackets>().eq("del_flag", DelFlagEnum.CODE.getCode()));

        Page<UnPackListResponse> responsePage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        List<UnPackListResponse> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(iPage.getRecords())) return responsePage;
        iPage.getRecords().forEach(o -> {
            UnPackListResponse response = new UnPackListResponse();
            response.setId(o.getId());
            response.setBegin(o.getIntervalBeginValue());
            response.setEnd(o.getIntervalEndValue());
            response.setName(o.getName());
            response.setProbability(o.getProbability());
            list.add(response);
        });
        responsePage.setRecords(list);
        return responsePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnpackLotteryResponse lottery(String userId) {
        //======判断用户是否还有拆红包机会
        Long grabCount = 0l;
        Long unpackCount = 0l;
        Map<String, Map<String, Long>> count = sysUserAccountMapper.getByIdCount(userId);
        for (Map<String, Long> value : count.values()) {
            grabCount += value.get("grabCount");
            unpackCount += value.get("unpackCount");
        }

        if (unpackCount >= grabCount) {
            throw new GlobalException("Không có cơ hội xổ số, bạn cần lấy một phong bì đỏ");
        }

        UnpackLotteryResponse response = new UnpackLotteryResponse();
        //查询所有拆红包配置
        List<UnpackRedPackets> unpackList = unpackList(null);
        if (CollectionUtils.isEmpty(unpackList)) throw new GlobalException("no data");

        //提取所有抢红包配置中奖概率
        List<BigDecimal> probability = unpackList.stream().map(o -> o.getProbability()).collect(Collectors.toList());

        //临时存放概率
        List<BigDecimal> probabilityList = new ArrayList<>();
        //计算总概率
        BigDecimal sum = BigDecimal.ZERO;
        probability.forEach(o -> {
            BigDecimal pro = o.divide(new BigDecimal(100));
            sum.add(pro);
        });

        //生成随机数
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double random = threadLocalRandom.nextDouble(0, 1);

        //把随机数加到概率集合并排序
        BigDecimal value = BigDecimal.valueOf(random);
        probabilityList.add(value);
        Collections.sort(probabilityList);
        //取出随机数的下标值
        int index = probabilityList.indexOf(value);

        BigDecimal gift = BigDecimal.ZERO;
        //如果随机数是集合起始下标 取集合第二位奖项
        if (index == 0) {
            gift = probabilityList.get(index + 1);
        } else if ((index == probabilityList.size() - 1)) {
            //如果随机数是集合结束下标 取集合倒数二位奖项
            gift = probabilityList.get(index - 1);
        } else {
            Boolean flag = Math.max(probabilityList.get(index - 1).doubleValue(), random) == Math.min(random, probabilityList.get(index + 1).doubleValue());
            if (flag) {
                gift = probabilityList.get(index + 1);
            }
        }

        //拆红包区间值
        BigDecimal multiply = gift.multiply(new BigDecimal(100));
        List<UnpackRedPackets> unpackOne = unpackList.stream().filter(o -> o.getProbability().compareTo(multiply) == 0).collect(Collectors.toList());
        int amount = threadLocalRandom.nextInt(unpackOne.get(0).getIntervalBeginValue(), unpackOne.get(0).getIntervalEndValue());
        response.setName(unpackOne.get(0).getName());
        response.setAmount(amount);

        QueryWrapper<ActingHierarchyRelation> actingQuery = new QueryWrapper<>();
        actingQuery.eq("create_by", userId);
        List<ActingHierarchyRelation> actingList = actingHierarchyRelationMapper.selectList(actingQuery);
        if (CollectionUtils.isEmpty(actingList)) {
            //新增拆红包明细
            SysUserAccount sysUserAccount = new SysUserAccount();
            sysUserAccount.setProductId(unpackOne.get(0).getId());
            sysUserAccount.setType("1");
            sysUserAccount.setSpending("0");
            sysUserAccount.setAmount(new BigDecimal(amount));
            sysUserAccount.setCreateBy(userId);
            sysUserAccountMapper.insert(sysUserAccount);
        }

        for (ActingHierarchyRelation o : actingList) {
            Double num = Double.valueOf(amount);
            switch (o.getActingId()) {
                case "1501592852484911106":
                    num = commission(o.getActingId(), o.getSuperiorId(), amount, unpackOne.get(0).getId());
                    break;
                case "1502622021805215745":
                    num = commission(o.getActingId(), o.getSuperiorId(), amount, unpackOne.get(0).getId());
                    break;
                case "1502622043628179459":
                    num = commission(o.getActingId(), o.getSuperiorId(), amount, unpackOne.get(0).getId());
                    break;
                default:
                    //新增拆红包明细
                    SysUserAccount sysUserAccount = new SysUserAccount();
                    sysUserAccount.setProductId(unpackOne.get(0).getId());
                    sysUserAccount.setType("1");
                    sysUserAccount.setSpending("0");
                    sysUserAccount.setAmount(new BigDecimal(num));
                    sysUserAccount.setCreateBy(userId);
                    sysUserAccountMapper.insert(sysUserAccount);
                    break;
            }
        }
        return response;
    }

    @Override
    public BroadcastResponse broadcast(Boolean flag) {
        BroadcastResponse response = new BroadcastResponse();

//        response = sysUserAccountMapper.broadcast("1");
//        if (ObjectUtil.isEmpty(response)) {
//            response = sysUserAccountMapper.broadcast("2");
//            if (ObjectUtil.isEmpty(response)) {
//                return sysBroadcastConfigMapper.broadcast();
//            } else {
//                response.setType("1");
//                return response;
//            }
//        }
//        response.setType("0");
        //return response;

        List<DismantleResponse> disList = sysUserAccountMapper.selectDis();
        List<GrabResponse> grabList = sysUserAccountMapper.selectGrab();
        if (!CollectionUtils.isEmpty(disList)) {
            response.setDisResp(disList);
        }
        if (!CollectionUtils.isEmpty(grabList)) {
            response.setGrabResp(grabList);
        }

        if (disList.size() + disList.size() < 50) {
            response.setRandomResp(sysBroadcastConfigMapper.selectRandom());
        }
        return response;
    }

    /**
     * 查询全部拆红包
     */
    private List<UnpackRedPackets> unpackList(String id) {
        QueryWrapper<UnpackRedPackets> query = new QueryWrapper<>();
        query.ne(ObjectUtil.isNotEmpty(id), "id", id);
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        return unpackRedPacketsMapper.selectList(query);
    }

    private double commission(String id, String userId, int amount, String unpackId) {
        double num = 0d;
        Acting acting = actingMapper.selectById(id);
        if (!ObjectUtil.isEmpty(acting)) {
            double i = acting.getCommissionRatio() * 0.01;
            double commission = amount * i;
            num = num - (amount * i);
            //新增拆红包明细
            SysUserAccount sysUserAccount = new SysUserAccount();
            sysUserAccount.setProductId(unpackId);
            sysUserAccount.setType("0");
            sysUserAccount.setSpending("0");
            sysUserAccount.setAmount(new BigDecimal(commission));
            sysUserAccount.setCreateBy(userId);
            sysUserAccountMapper.insert(sysUserAccount);
        }
        return num;
    }
}

