package com.vietnam.lottery.business.unpackRedPackets.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拆红包配置(UnpackRedPackets)表实体类
 *
 * @author makejava
 * @since 2022-02-16 18:00:37
 */
@SuppressWarnings("serial")
public class UnpackRedPackets extends Model<UnpackRedPackets> {
    //id
    private Long id;
    //奖项名称
    private String name;
    //区间开始值
    private Integer intervalBeginValue;
    //区间结束值
    private Integer intervalEndValue;
    //中奖概率
    private BigDecimal probability;
    //删除标志(0正常 1停用)
    private String delFlag;
    //创建人
    private Long createBy;
    //创建时间
    private Date createDate;
    //修改人
    private Long updateBy;
    //修改时间
    private Date updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIntervalBeginValue() {
        return intervalBeginValue;
    }

    public void setIntervalBeginValue(Integer intervalBeginValue) {
        this.intervalBeginValue = intervalBeginValue;
    }

    public Integer getIntervalEndValue() {
        return intervalEndValue;
    }

    public void setIntervalEndValue(Integer intervalEndValue) {
        this.intervalEndValue = intervalEndValue;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
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

