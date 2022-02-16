package com.vietnam.lottery.business.grabRedPackets.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抢红包(GrabRedPackets)表实体类
 *
 * @author makejava
 * @since 2022-02-16 18:00:21
 */
@SuppressWarnings("serial")
public class GrabRedPackets extends Model<GrabRedPackets> {
    //id
    private Long id;
    //金额
    private BigDecimal amount;
    //区间开始值
    private Integer intervalBeginValue;
    //区间结束值
    private Integer intervalEndValue;
    //删除标志(0正常 1停用)
    private String delFlag;
    //创建人
    private Long createBy;
    //创建时间
    private Date createDate;
    //修改人
    private Long updateBy;
    //修改时间
    private Date updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
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

