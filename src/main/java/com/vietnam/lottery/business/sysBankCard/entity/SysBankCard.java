package com.vietnam.lottery.business.sysBankCard.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 银行信息(SysBankCard)表实体类
 *
 * @author Gyan
 * @since 2022-04-10 14:41:00
 */
@SuppressWarnings("serial")
public class SysBankCard extends Model<SysBankCard> {
    //id
    private String id;
    //银行名称
    private String backName;
    //银行编号
    private String bankId;
    //银行简称
    private String bankAbbreviation;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankAbbreviation() {
        return bankAbbreviation;
    }

    public void setBankAbbreviation(String bankAbbreviation) {
        this.bankAbbreviation = bankAbbreviation;
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

