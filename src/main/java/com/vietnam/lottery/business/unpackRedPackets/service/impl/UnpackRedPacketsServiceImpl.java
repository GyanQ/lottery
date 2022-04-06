package com.vietnam.lottery.business.unpackRedPackets.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackDetailResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnPackListResponse;
import com.vietnam.lottery.business.unpackRedPackets.response.UnpackLotteryResponse;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel add(UnPackAddRequest request) {
        List<UnpackRedPackets> unpackList = unpackList();
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = new BigDecimal(100);
            if (total != probability) {
                return ResultUtil.failure("The probabilities do not add up to 100");
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

        List<UnpackRedPackets> unpackList = unpackList();
        if (!CollectionUtils.isEmpty(unpackList)) {
            BigDecimal probability = unpackList.stream().map(UnpackRedPackets::getProbability).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = new BigDecimal(100);
            if (total != probability) {
                return ResultUtil.failure("The probabilities do not add up to 100");
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
        UnpackLotteryResponse response = new UnpackLotteryResponse();
        //查询所有拆红包配置
        List<UnpackRedPackets> unpackList = unpackList();
        if (CollectionUtils.isEmpty(unpackList)) throw new GlobalException("no data");

        //提取所有中奖概率
        List<BigDecimal> probability = unpackList.stream().map(o -> o.getProbability()).collect(Collectors.toList());

        //临时存放概率
        List<BigDecimal> probabilityList = new ArrayList<>();
        probability.forEach(o -> {
            BigDecimal pro = o.divide(new BigDecimal(100));
            pro.add(pro);
        });

        //生成随机数
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double random = threadLocalRandom.nextDouble(0, 1);

        //把随机数加到概率集合并排序
        probabilityList.add(BigDecimal.valueOf(random));
        Collections.sort(probabilityList);
        //取出随机数的下标值
        int index = probabilityList.indexOf(random);

        BigDecimal gift = BigDecimal.ZERO;
        //如果随机数是集合起始下标 取集合第二位奖项
        if (index == 0) {
            gift = probabilityList.get(index + 1);
        }
        //如果随机数是集合结束下标 取集合倒数二位奖项
        if (index == probabilityList.size()) {
            gift = probabilityList.get(index - 1);
        }

        Boolean flag = Math.max(probabilityList.get(index - 1).doubleValue(), random) == Math.min(random, probabilityList.get(index + 1).doubleValue());
        if (flag) {
            gift = probabilityList.get(index + 1);
        } else {
            throw new GlobalException("error");
        }
        //拆红包区间值
        BigDecimal multiply = gift.multiply(new BigDecimal(100));
        List<UnpackRedPackets> unpackOne = unpackList.stream().filter(o -> o.getProbability().compareTo(multiply) == 0).collect(Collectors.toList());
        int amount = threadLocalRandom.nextInt(unpackOne.get(0).getIntervalBeginValue(), unpackOne.get(0).getIntervalEndValue());
        response.setName(unpackOne.get(0).getName());
        response.setAmount(amount);

        //新增拆红包明细
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setProductId(unpackOne.get(0).getId());
        sysUserAccount.setType("1");
        sysUserAccount.setSpending("0");
        sysUserAccount.setAmount(new BigDecimal(amount));
        sysUserAccount.setCreateBy(userId);
        sysUserAccountMapper.insert(sysUserAccount);
        return response;
    }

    /**
     * 查询全部拆红包
     */
    private List<UnpackRedPackets> unpackList() {
        QueryWrapper<UnpackRedPackets> query = new QueryWrapper<>();
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        return unpackRedPacketsMapper.selectList(query);
    }
}

