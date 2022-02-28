package com.vietnam.lottery.business.sysSms.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 短信(SysSms)表实体类
 *
 * @author Gyan
 * @since 2022-02-28 15:10:18
 */
@SuppressWarnings("serial")
public class SysSms extends Model<SysSms> {
    //id
    private Long id;
    //手机号
    private String phone;
    //验证码
    private String code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

