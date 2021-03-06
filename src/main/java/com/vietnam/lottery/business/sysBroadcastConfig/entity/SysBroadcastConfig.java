package com.vietnam.lottery.business.sysBroadcastConfig.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 广播配置(SysBroadcastConfig)表实体类
 *
 * @author Gyan
 * @since 2022-05-03 22:16:02
 */
@SuppressWarnings("serial")
public class SysBroadcastConfig extends Model<SysBroadcastConfig> {
    //id
    private String id;
    //金额
    private BigDecimal amount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

