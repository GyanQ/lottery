package com.vietnam.lottery.business.sysLoginDetail.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录详情(SysLoginDetail)表实体类
 *
 * @author Gyan
 * @since 2022-02-18 11:50:26
 */
@SuppressWarnings("serial")
public class SysLoginDetail extends Model<SysLoginDetail> {
    //id
    private String id;
    //临时用户(0是 1否)
    private String temporaryUser;
    //ip地址
    private String ip;
    //创建人
    private String createBy;
    //创建时间
    private Date createDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemporaryUser() {
        return temporaryUser;
    }

    public void setTemporaryUser(String temporaryUser) {
        this.temporaryUser = temporaryUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

