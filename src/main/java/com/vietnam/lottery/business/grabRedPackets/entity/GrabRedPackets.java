package com.vietnam.lottery.business.grabRedPackets.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 抢红包配置(GrabRedPackets)表实体类
 *
 * @author makejava
 * @since 2022-02-16 18:00:21
 */
@SuppressWarnings("serial")
public class GrabRedPackets extends Model<GrabRedPackets> {
    //id
    private String id;
    //金额
    private Long amount;
    //区间开始值
    private Integer intervalBeginValue;
    //区间结束值
    private Integer intervalEndValue;
    //删除标志(0正常 1停用)
    private String delFlag;
    //创建人
    private String createBy;
    //创建时间
    private Date createDate;
    //修改人
    private String updateBy;
    //修改时间
    private Date updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getIntervalBeginValue() {
        return intervalBeginValue;
    }

    public void setIntervalBeginValue(Integer intervalBeginValue) {
        this.intervalBeginValue = intervalBeginValue;
    }

    public Integer getIntervalEndValue() {
        return intervalEndValue;
    }

    public void setIntervalEndValue(Integer intervalEndValue) {
        this.intervalEndValue = intervalEndValue;
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

