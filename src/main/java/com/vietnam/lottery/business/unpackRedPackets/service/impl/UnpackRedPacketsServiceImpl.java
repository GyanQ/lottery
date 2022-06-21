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
import com.vietnam.lottery.business.unpackRedPackets.response.BroadcastResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackDetailResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackListResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnpackLotteryResponse;
import com.vietnam.lottery.business.unpackRedPackets.service.UnpackRedPacketsService;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
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
        List<UnpackRedPackets> unpackList = unpackList(null, request.getGrabId());
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = probability.add(request.getProbability());
            if (total.compareTo(new BigDecimal(100)) == 1) {
                return ResultUtil.failure("拆红包概率大于100%");
            }
        }
        UnpackRedPackets unpackRedPackets = new UnpackRedPackets();
        unpackRedPackets.setGrabRedPacketsId(request.getGrabId());
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

        List<UnpackRedPackets> unpackList = unpackList(unpackRedPackets.getId(), request.getGrabId());
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = probability.add(request.getProbability());
            if (total.compareTo(new BigDecimal(100)) == 1) {
                return ResultUtil.failure("拆红包概率大于100%");
            }
        }

        unpackRedPackets.setGrabRedPacketsId(request.getGrabId());
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
        QueryWrapper<UnpackRedPackets> query = new QueryWrapper<>();
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        query.eq(!StringUtils.isBlank(request.getGrabId()), "grab_red_packets_id", request.getGrabId());
        Page<UnpackRedPackets> iPage = unpackRedPacketsMapper.selectPage(page, query);

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
    public UnpackLotteryResponse lottery(String userId, String language) {
        UnpackLotteryResponse resp = new UnpackLotteryResponse();
        //======判断用户是否还有拆红包机会
        QueryWrapper<SysUserAccount> grabQuery = new QueryWrapper<>();
        grabQuery.eq("create_by", userId);
        grabQuery.eq("type", "2");
        grabQuery.eq("lottery_status", "0");
        List<SysUserAccount> grabList = sysUserAccountMapper.selectList(grabQuery);
        if (CollectionUtils.isEmpty(grabList)) {
            if ("0".equals(language)) {
                throw new GlobalException("Insuffcient withdraw chance");
            } else {
                throw new GlobalException("Số liệu túi khuyến mãi trống");
            }
        }
//        QueryWrapper<SysUserAccount> unpackQuery = new QueryWrapper<>();
//        unpackQuery.eq("create_by", userId);
//        unpackQuery.eq("type", "1");
//        List<SysUserAccount> unpackList = sysUserAccountMapper.selectList(unpackQuery);

        if (CollectionUtils.isEmpty(grabList)) throw new GlobalException("");

        //List<SysUserAccount> collect = grabList.stream().filter(o -> !unpackList.contains(o)).collect(Collectors.toList());
        //查询所有拆红包配置
        List<UnpackRedPackets> unpackConfig = unpackList(null, grabList.get(0).getProductId());
        int index = lottery(unpackConfig);

        int amount = ThreadLocalRandom.current().nextInt(unpackConfig.get(index).getIntervalBeginValue(), unpackConfig.get(index).getIntervalEndValue());
        //代理
        QueryWrapper<ActingHierarchyRelation> actingQuery = new QueryWrapper<>();
        actingQuery.eq("create_by", userId);
        List<ActingHierarchyRelation> actingList = actingHierarchyRelationMapper.selectList(actingQuery);

        BigDecimal num = new BigDecimal(amount);

        if (!CollectionUtils.isEmpty(actingList)) {
            for (ActingHierarchyRelation o : actingList) {
                switch (o.getActingId()) {
                    case "1501592852484911106":
                        num = commission(o.getActingId(), o.getSuperiorId(), num, unpackConfig.get(index).getId());
                        break;
                    case "1502622021805215745":
                        num = commission(o.getActingId(), o.getSuperiorId(), num, unpackConfig.get(index).getId());
                        break;
                    case "1502622043628179459":
                        num = commission(o.getActingId(), o.getSuperiorId(), num, unpackConfig.get(index).getId());
                        break;
                }
            }
        }
        //新增拆红包明细
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setProductId(unpackConfig.get(index).getId());
        sysUserAccount.setType("1");
        sysUserAccount.setSpending("0");
        sysUserAccount.setAmount(num.setScale(0, BigDecimal.ROUND_DOWN));
        sysUserAccount.setCreateBy(userId);
        sysUserAccountMapper.insert(sysUserAccount);

        SysUserAccount grabOrder = sysUserAccountMapper.selectById(grabList.get(0).getId());
        if (ObjectUtil.isEmpty(grabOrder)) throw new GlobalException("");
        grabOrder.setLotteryStatus("1");
        grabOrder.setUpdateBy(userId);
        grabOrder.setUpdateDate(new Date());
        sysUserAccountMapper.updateById(grabOrder);

        resp.setAmount(num.setScale(0, BigDecimal.ROUND_DOWN));
        resp.setName(unpackConfig.get(index).getName());
        return resp;
    }

    @Override
    public List<BroadcastResponse> broadcast(Boolean flag) {
        List<BroadcastResponse> resp = new ArrayList<>();
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

        List<BroadcastResponse> disList = sysUserAccountMapper.selectDis();
        if (!CollectionUtils.isEmpty(disList)) {
            disList.forEach(o -> {
                o.setType("0");
            });
            List<BroadcastResponse> grabList = sysUserAccountMapper.selectGrab();
            if (!CollectionUtils.isEmpty(grabList)) {
                grabList.forEach(o -> {
                    o.setType("1");
                });
            }
            if (disList.size() + grabList.size() <= 50) {
                List<BroadcastResponse> ranList = sysBroadcastConfigMapper.selectRandom();
                ranList.forEach(o -> {
                    o.setType("0");
                });
            } else {
                resp.addAll(disList);
                resp.addAll(grabList);
                return resp.stream().sorted(Comparator.comparing(BroadcastResponse::getType)).collect(Collectors.toList());
            }
        }
        List<BroadcastResponse> ranList = sysBroadcastConfigMapper.selectRandom();
        ranList.forEach(o -> {
            o.setType("0");
        });
        return ranList;
    }

    /**
     * 查询全部拆红包
     */
    private List<UnpackRedPackets> unpackList(String id, String grabId) {
        QueryWrapper<UnpackRedPackets> query = new QueryWrapper<>();
        query.eq(ObjectUtil.isNotEmpty(grabId), "grab_red_packets_id", grabId);
        query.ne(ObjectUtil.isNotEmpty(id), "id", id);
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        query.orderByDesc("create_date");
        return unpackRedPacketsMapper.selectList(query);
    }

    @Transactional(rollbackFor = Exception.class)
    public BigDecimal commission(String id, String userId, BigDecimal amount, String unpackId) {
        BigDecimal num = BigDecimal.ZERO;
        Acting acting = actingMapper.selectById(id);
        if (!ObjectUtil.isEmpty(acting)) {
            double i = acting.getCommissionRatio() * 0.01;
            //上级分佣金额
            BigDecimal commission = amount.multiply(new BigDecimal(new Double(i).toString()));
            //用户实际得到的金额
            num = amount.subtract(commission);
            //新增拆红包明细
            SysUserAccount sysUserAccount = new SysUserAccount();
            sysUserAccount.setProductId(unpackId);
            sysUserAccount.setType("0");
            sysUserAccount.setSpending("0");
            sysUserAccount.setAmount(commission);
            sysUserAccount.setCreateBy(userId);
            sysUserAccountMapper.insert(sysUserAccount);
        }
        return num;
    }

    /**
     * 抽奖
     */
    private int lottery(List<UnpackRedPackets> list) {
        if (CollectionUtils.isEmpty(list)) throw new GlobalException("");
        //计算总概率 换算成1
        double total = 0d;
        List<Double> collect = list.stream().map(o -> o.getProbability().doubleValue()).collect(Collectors.toList());
//        for (UnpackRedPackets unpackRedPackets : list) {
//            total = total.add(unpackRedPackets.getProbability().multiply(new BigDecimal(0.01)));
//        }
        for (Double o : collect) {
            total += (o * 0.01);
        }

        double temp = 0d;
        //计算每个奖项在总概率下的概率
        List<Double> probabilityList = new ArrayList<>();
        for (Double o : collect) {
            temp += (o * 0.01);
            probabilityList.add(temp / total);
        }
        //根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        probabilityList.add(nextDouble);
        Collections.sort(probabilityList);
        return probabilityList.indexOf(nextDouble);
    }
}

