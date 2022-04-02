package com.vietnam.lottery.business.sysBankCard.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户银行信息(SysBankCard)表实体类
 *
 * @author Gyan
 * @since 2022-04-01 10:49:50
 */
@SuppressWarnings("serial")
public class SysBankCard extends Model<SysBankCard> {
    //id
    private String id;
    //收款人姓名
    private String collectionName;
    //收款人卡号
    private String cardNum;
    //银行编号
    private String cardSerialNum;
    //银行名称
    private String backName;
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

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardSerialNum() {
        return cardSerialNum;
    }

    public void setCardSerialNum(String cardSerialNum) {
        this.cardSerialNum = cardSerialNum;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
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

