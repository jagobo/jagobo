package com.bc.cas.dao.impl;

import com.bc.cas.dao.CmCaseSqlDao;
import com.bc.cas.model.entity.CmCase;
import com.bc.core.Exception.MyException;
import com.bc.common.dao.BaseQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc
 */

@Repository
public class CmCaseSqlDaoImpl implements CmCaseSqlDao {

    @Autowired
    private BaseQueryDao baseQueryDao;


    /**
     * 根据类型查询列表
     *
     * @param type
     * @throws MyException
     */
    @Override
    public List<CmCase> findByType(int type) throws MyException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        String sql = "select * from cm_case where type = :type";
        List<CmCase> caseList = baseQueryDao.queryListByConditionModel(CmCase.class, sql, params);
        return caseList;
    }

}
