package com.bc.cas.model.entity;

import com.bc.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc 案件实体类
 */
@Entity
@Table(name = "cm_case")
public class CmCase extends BaseEntity {

    @Column(name = "name")
    private String name; //案件名称

    @Column(name = "description")
    private String description;//案件描述

    @Column(name = "type")
    private Integer type;//案件类别

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CmCase{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
