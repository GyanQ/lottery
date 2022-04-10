package com.vietnam.lottery.business.sysUserBankCard.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户银行卡详情(SysUserBankCard)表实体类
 *
 * @author Gyan
 * @since 2022-04-10 14:49:52
 */
@SuppressWarnings("serial")
public class SysUserBankCard extends Model<SysUserBankCard> {
    //id
    private String id;
    //收款人姓名
    private String cardName;
    //收款人卡号
    private String cardNo;
    //银行编号
    private String bankId;
    //银行名称
    private String backName;
    //银行简称
    private String bankAbbreviation;
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getBankAbbreviation() {
        return bankAbbreviation;
    }

    public void setBankAbbreviation(String bankAbbreviation) {
        this.bankAbbreviation = bankAbbreviation;
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

