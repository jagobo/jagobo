package com.bc.cas.dao;

import com.bc.cas.model.entity.CmCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 206/10/26.
 * @Version V 1.0.0
 * @Desc 案件类型查询基础接口
 */

@Repository
public interface CmCaseDao extends JpaRepository<CmCase, Long> {

    /**
     * 查询列表
     *
     * @param type 案件类型
     * @return
     */
    List<CmCase> findByType(Integer type);
}
