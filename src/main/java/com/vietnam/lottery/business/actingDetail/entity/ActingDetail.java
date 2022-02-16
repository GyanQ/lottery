package com.vietnam.lottery.business.actingDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 代理详情(ActingDetail)表实体类
 *
 * @author makejava
 * @since 2022-02-16 11:41:30
 */
@SuppressWarnings("serial")
public class ActingDetail extends Model<ActingDetail> {
    //id
    private Long id;
    //代理层级关系id
    private Long hierarchyRelationId;
    //消费金额
    private Double amount;
    //分佣金额
    private Double commissionAmount;
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

    public Long getHierarchyRelationId() {
        return hierarchyRelationId;
    }

    public void setHierarchyRelationId(Long hierarchyRelationId) {
        this.hierarchyRelationId = hierarchyRelationId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Double commissionAmount) {
        this.commissionAmount = commissionAmount;
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

