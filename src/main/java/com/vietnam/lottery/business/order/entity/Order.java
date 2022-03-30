package com.vietnam.lottery.business.order.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单(Order)表实体类
 *
 * @author Gyan
 * @since 2022-03-01 14:49:22
 */
@SuppressWarnings("serial")
public class Order extends Model<Order> {
    //id
    private String id;
    //抢红包配置id
    private String grabRedPacketsId;
    //支付类型(1zalo 2momo)
    private String payType;
    //支付状态(1待支付 2已支付 3取消支付)
    private String payStatus;
    //删除标志（0代表存在、1代表删除）
    private String delFlag;
    //创建人
    private String createBy;
    //创建时间
    private Date createDate;
    //修改人
    private String updateBy;
    //更新时间
    private Date updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrabRedPacketsId() {
        return grabRedPacketsId;
    }

    public void setGrabRedPacketsId(String grabRedPacketsId) {
        this.grabRedPacketsId = grabRedPacketsId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

