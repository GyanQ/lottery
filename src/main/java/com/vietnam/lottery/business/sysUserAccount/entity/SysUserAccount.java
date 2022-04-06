package com.vietnam.lottery.business.sysUserAccount.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户明细(SysUserAccount)表实体类
 *
 * @author Gyan
 * @since 2022-04-05 22:30:49
 */
@SuppressWarnings("serial")
public class SysUserAccount extends Model<SysUserAccount> {
    //id
    private String id;
    //产品id
    private String productId;
    //类型(0分佣 1拆红包 2抢红包 3提现)
    private String type;
    //开支(0支入 1支出)
    private String spending;
    //金额
    private Double amount;
    //审核状态(1未审核 2审核未通过 3审核通过 4审核中)
    private String audit;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
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

