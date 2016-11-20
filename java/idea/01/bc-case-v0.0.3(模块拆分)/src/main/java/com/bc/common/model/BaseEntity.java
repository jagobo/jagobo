package com.bc.common.model;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


/**
 * 实体类的基类, 包含了对
 */
@MappedSuperclass
public class BaseEntity extends AutoIncrement {

    @Column(name = "creater", updatable = false)
    protected Long creater;

    @Column(name = "create_time", updatable = false)
    protected Date createTime;

    @Column(name = "modifier")
    protected Long modifier;

    @Column(name = "modify_time")
    protected Date modifyTime;

    protected Long version = 0L;


    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }


    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @PreUpdate
    public void preUpdate() {
        setModifyTime(new Date());
    }

    @PrePersist
    public void PrePersist() {
        if (this.getCreateTime() == null) {
            setCreateTime(new Date());
        }
        setModifyTime(new Date());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "creater=" + creater +
                ", createTime=" + createTime +
                ", modifier=" + modifier +
                ", modifyTime=" + modifyTime +
                ", version=" + version +
                '}';
    }
}
