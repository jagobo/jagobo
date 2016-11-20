package com.bc.cas.service.impl;


import com.bc.cas.dao.CmCaseDao;
import com.bc.cas.dao.CmCaseSqlDao;
import com.bc.cas.model.entity.CmCase;
import com.bc.cas.service.CmCaseService;
import com.bc.core.Exception.MyException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc
 */
@Service
public class CmCaseServiceImpl implements CmCaseService {

    private static final Logger l = org.slf4j.LoggerFactory.getLogger(CmCaseServiceImpl.class);

    @Autowired
    private CmCaseSqlDao cmCaseSqlDao;
    @Autowired
    private CmCaseDao cmCaseDao;


    @Override
    public List<CmCase> findByType(int type) throws MyException {

        List<CmCase> list1 = cmCaseDao.findByType(1001);
//        List<CmCase> list2 = cmCaseSqlDao.findByType(1001);

        System.out.printf("list1=" + list1);
//        System.out.printf("list2=" + list2);
        return list1;

    }


}

