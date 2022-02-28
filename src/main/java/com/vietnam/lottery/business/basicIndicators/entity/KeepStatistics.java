package com.vietnam.lottery.business.basicIndicators.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 留存统计(KeepStatistics)表实体类
 *
 * @author makejava
 * @since 2022-02-28 14:44:52
 */
@SuppressWarnings("serial")
public class KeepStatistics extends Model<KeepStatistics> {
    //id
    private Long id;
    //创建人
    private Long createBy;
    //注册时间
    private Date registerDate;
    //创建时间
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

