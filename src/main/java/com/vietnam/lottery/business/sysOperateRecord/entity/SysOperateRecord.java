package com.vietnam.lottery.business.sysOperateRecord.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录(SysOperateRecord)表实体类
 *
 * @author Gyan
 * @since 2022-02-09 10:45:30
 */
@SuppressWarnings("serial")
public class SysOperateRecord extends Model<SysOperateRecord> {
    //id
    private String id;
    //模块
    private String module;
    //操作
    private String operate;
    //内容
    private String content;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

