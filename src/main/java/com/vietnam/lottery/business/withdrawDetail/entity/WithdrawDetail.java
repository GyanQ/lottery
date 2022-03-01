package com.vietnam.lottery.business.withdrawDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 提现记录(WithdrawDetail)表实体类
 *
 * @author Gyan
 * @since 2022-02-17 10:50:07
 */
@SuppressWarnings("serial")
public class WithdrawDetail extends Model<WithdrawDetail> {
    //id
    private Long id;
    //commission_detail_id
    private Long commissionDetailId;
    //lottery_detail_id
    private Long lotteryDetailId;
    //金额
    private Long amount;
    //审核状态(1未审核 2审核未通过 3审核通过)
    private String audit;
    //提现状态(1提现中 2已提现)
    private String state;
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

    public Long getCommissionDetailId() {
        return commissionDetailId;
    }

    public void setCommissionDetailId(Long commissionDetailId) {
        this.commissionDetailId = commissionDetailId;
    }

    public Long getLotteryDetailId() {
        return lotteryDetailId;
    }

    public void setLotteryDetailId(Long lotteryDetailId) {
        this.lotteryDetailId = lotteryDetailId;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

