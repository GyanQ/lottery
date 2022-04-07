package com.vietnam.lottery.business.customer.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服配置(Customer)表实体类
 *
 * @author Gyan
 * @since 2022-04-07 23:31:37
 */
@SuppressWarnings("serial")
public class Customer extends Model<Customer> {
    //id
    private String id;
    //客服图片
    private String url;
    //tele账号
    private String teleAccount;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTeleAccount() {
        return teleAccount;
    }

    public void setTeleAccount(String teleAccount) {
        this.teleAccount = teleAccount;
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

