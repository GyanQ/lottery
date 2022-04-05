package com.vietnam.lottery.business.grabRedPacketsDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 开奖记录(LotteryDetail)表实体类
 *
 * @author Gyan
 * @since 2022-02-17 12:17:13
 */
@SuppressWarnings("serial")
public class GrabRedPacketsDetail extends Model<GrabRedPacketsDetail> {
    //id
    private String id;
    //抢红包id
    private String grabRedPacketsId;
    //抢红包金额
    private Long amount;
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

    public String getGrabRedPacketsId() {
        return grabRedPacketsId;
    }

    public void setGrabRedPacketsId(String grabRedPacketsId) {
        this.grabRedPacketsId = grabRedPacketsId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

