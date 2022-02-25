package com.vietnam.lottery.business.actingCommissionDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理分佣明细(ActingCommissionDetail)表实体类
 *
 * @author Gyan
 * @since 2022-02-16 11:41:30
 */
@SuppressWarnings("serial")
public class ActingCommissionDetail extends Model<ActingCommissionDetail> {
    //id
    private Long id;
    //分佣金额
    private BigDecimal amount;
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

