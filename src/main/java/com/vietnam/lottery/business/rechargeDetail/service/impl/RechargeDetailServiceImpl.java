package com.vietnam.lottery.business.rechargeDetail.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vietnam.lottery.business.grabRedPackets.mapper.GrabRedPacketsMapper;
import com.vietnam.lottery.business.grabRedPacketsDetail.request.PayRequest;
import com.vietnam.lottery.business.rechargeDetail.mapper.RechargeDetailMapper;
import com.vietnam.lottery.business.rechargeDetail.request.RechargeListRequest;
import com.vietnam.lottery.business.rechargeDetail.response.RechargeListResponse;
import com.vietnam.lottery.business.rechargeDetail.service.RechargeDetailService;
import com.vietnam.lottery.business.sysUser.mapper.SysUserMapper;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 订单(Order)表服务实现类
 *
 * @author Gyan
 * @since 2022-03-01 14:49:24
 */
@Service("rechargeDetail")
@Slf4j
public class RechargeDetailServiceImpl implements RechargeDetailService {
    @Autowired
    private RechargeDetailMapper rechargeDetailMapper;
    @Resource
    private RechargeDetailMapper orderMapper;
    @Resource
    private GrabRedPacketsMapper grabRedPacketsMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Page<RechargeListResponse> list(RechargeListRequest request) {
        Page<RechargeListResponse> page = new Page<>(request.getCurrent(), request.getSize());
        return rechargeDetailMapper.list(page, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel pay(PayRequest request) {
//        Order order = orderMapper.selectById(request.getOrderNo());
//        if (ObjectUtil.isEmpty(order)) return ResultUtil.failure("Can't find order information");
//
//        GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(order.getGrabRedPacketsId());
//        if (ObjectUtil.isEmpty(grabRedPackets)) return ResultUtil.failure("Can't find the amount of the red envelope");
//
//        QueryWrapper<RechargeDetail> query = new QueryWrapper<>();
//        query.eq("create_by", request.getCreateBy());
//        query.eq("del_flag", DelFlagEnum.CODE.getCode());
//        List<RechargeDetail> rechargeDetailList = rechargeDetailMapper.selectList(query);
//
//        //首次充值赠送20000盾
//        Long amount = 20000l;
//        if (CollectionUtils.isEmpty(rechargeDetailList)) {
//            amount += grabRedPackets.getAmount();
//        }
//        //支付
//        CreateOrderRequest orderRequest = new CreateOrderRequest();
//        orderRequest.setOrderId(request.getOrderNo());
//        orderRequest.setAmount(amount);
//        orderRequest.setType(request.getType());
//        log.info("支付传参:{}", orderRequest);
//        String str = PaymentUtils.createOrder(orderRequest);
//        log.info("请求第三方支付返回结果:{}", str);
//        JSONObject json = JSONUtil.parseObj(str);
//        JSONObject data = json.getJSONObject("data");
//        log.info("获取data,{}", data);
//        if (!"success".equals(json.get("msg"))) {
//            throw new GlobalException("Failed to create payment order");
//        }
//        //更新订单支付类型
//        order.setPayType(request.getType());
//        orderMapper.updateById(order);
//        //增加充值记录
//        RechargeDetail rechargeDetail = new RechargeDetail();
//        rechargeDetail.setStatus("0");
//        rechargeDetail.setCreateBy(order.getCreateBy());
//        rechargeDetailMapper.insert(rechargeDetail);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callBack(String body) {
//        log.info("回调信息:{}", body);
//        JSONObject json = JSONUtil.parseObj(body);
//        JSONObject data = json.getJSONObject("data");
//        Integer isPay = data.getInt("ispay");
//        String orderNo = data.getStr("orderid");
//        Order order = orderMapper.selectById(orderNo);
//        //支付成功
//        if (1 == isPay && null != order) {
//            GrabRedPackets grabRedPackets = grabRedPacketsMapper.selectById(order.getGrabRedPacketsId());
//            //增加充值记录
//            RechargeDetail rechargeDetail = new RechargeDetail();
//            rechargeDetail.setAmount(data.getLong("amount"));
//            rechargeDetail.setStatus("1");
//            rechargeDetail.setCreateBy(order.getCreateBy());
//            rechargeDetailMapper.insert(rechargeDetail);
//            //更新订单支付状态
//            order.setPayStatus(StatusEnum.FINISH_PAY.getCode());
//            orderMapper.updateById(order);
//            //更新用户余额
//            SysUser user = sysUserMapper.selectById(order.getCreateBy());
//            Long amount = user.getAmount() - grabRedPackets.getAmount();
//            user.setAmount(amount);
//            user.setUpdateBy(order.getCreateBy());
//            user.setUpdateDate(new Date());
//            sysUserMapper.updateById(user);
    }
}


