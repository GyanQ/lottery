package com.vietnam.lottery.business.grabRedPacketsDetail.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.entity.GrabRedPackets;
import com.vietnam.lottery.business.grabRedPackets.mapper.GrabRedPacketsMapper;
import com.vietnam.lottery.business.grabRedPacketsDetail.mapper.GrabRedPacketsDetailMapper;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.LotteryListRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.PayRequest;
import com.vietnam.lottery.business.grabRedPacketsDetail.response.LotteryListResponse;
import com.vietnam.lottery.business.grabRedPacketsDetail.service.GrabRedPacketsDetailsService;
import com.vietnam.lottery.business.order.entity.Order;
import com.vietnam.lottery.business.order.mapper.OrderMapper;
import com.vietnam.lottery.business.order.request.CreateOrderRequest;
import com.vietnam.lottery.business.rechargeDetail.entity.RechargeDetail;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.common.config.PaymentUtils;
import com.vietnam.lottery.common.global.DelFlagEnum;
import com.vietnam.lottery.common.global.GlobalException;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 开奖记录(LotteryDetail)表服务实现类
 *
 * @author Gyan
 * @since 2022-02-17 12:17:14
 */
@Service("grabRedPacketsDetail")
@Slf4j
public class GrabRedPacketsDetailsServiceImpl implements GrabRedPacketsDetailsService {
    @Autowired
    private GrabRedPacketsDetailMapper lotteryDetailMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GrabRedPacketsMapper grabRedPacketsMapper;
    @Resource
    private RechargeDetailMapper rechargeDetailMapper;

    @Override
    public Page<LotteryListResponse> list(LotteryListRequest request) {
        Page<LotteryListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return lotteryDetailMapper.list(page, request);
    }

    @Override
    public ResultModel pay(PayRequest request) {
        Order order = orderMapper.selectById(request.getOrderNo());
        if (ObjectUtil.isEmpty(order)) return ResultUtil.failure("Can't find order information");

        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(order.getGrabRedPacketsId());
        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("Can't find the amount of the red envelope");

        QueryWrapper<RechargeDetail> query = new QueryWrapper<>();
        query.eq("create_by", request.getCreateBy());
        query.eq("del_flag", DelFlagEnum.CODE.getCode());
        List<RechargeDetail> rechargeDetailList = rechargeDetailMapper.selectList(query);

        //首次充值赠送20000盾
        Long amount = 20000l;
        if (CollectionUtils.isEmpty(rechargeDetailList)) {
            amount += grabRedPackets.getAmount();
        }
        //支付
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setOrderId(request.getOrderNo());
        orderRequest.setAmount(amount);
        orderRequest.setType(request.getType());
        log.info("支付传参:{}", orderRequest);
        String str = PaymentUtils.createOrder(orderRequest);
        log.info("请求第三方支付返回结果:{}", str);
        JSONObject json = JSONUtil.parseObj(str);
        JSONObject data = json.getJSONObject("data");
        log.info("获取data,{}", data);
        if (!"success".equals(json.get("msg"))) {
            throw new GlobalException("Failed to create payment order");
        }
        //更新订单支付类型
        order.setPayType(request.getType());
        orderMapper.updateById(order);
        return ResultUtil.success(data);
    }
}

