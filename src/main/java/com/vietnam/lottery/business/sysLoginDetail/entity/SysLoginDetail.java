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
    private Long id;
    //访问方式(1临时用户 2登录用户)
    private String interviewMethod;
    //ip地址
    private String ip;
    //创建人
    private Long createBy;
    //创建时间
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterviewMethod() {
        return interviewMethod;
    }

    public void setInterviewMethod(String interviewMethod) {
        this.interviewMethod = interviewMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

