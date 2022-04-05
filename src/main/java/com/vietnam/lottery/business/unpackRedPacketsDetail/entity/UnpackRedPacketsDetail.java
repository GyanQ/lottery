package com.vietnam.lottery.business.unpackRedPacketsDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 拆红包明细(UnpackRedPacketsDetail)表实体类
 *
 * @author makejava
 * @since 2022-04-05 09:49:57
 */
@SuppressWarnings("serial")
public class UnpackRedPacketsDetail extends Model<UnpackRedPacketsDetail> {
    //id
    private String id;
    //拆红包id
    private String unpackRedPacketsId;
    //拆红包金额
    private Long amount;
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

    public String getUnpackRedPacketsId() {
        return unpackRedPacketsId;
    }

    public void setUnpackRedPacketsId(String unpackRedPacketsId) {
        this.unpackRedPacketsId = unpackRedPacketsId;
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

