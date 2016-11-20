package com.bc.cas.dao;

import com.bc.cas.model.entity.CmCase;
import com.bc.core.Exception.MyException;

import java.util.List;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc
 */
public interface CmCaseSqlDao {
    /**
     * 根据类型查询列表
     *
     * @param type
     * @throws MyException
     */
    List<CmCase> findByType(int type) throws MyException;


}
