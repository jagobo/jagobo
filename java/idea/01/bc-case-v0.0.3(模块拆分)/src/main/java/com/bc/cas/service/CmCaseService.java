package com.bc.cas.service;

import com.bc.cas.model.entity.CmCase;
import com.bc.core.Exception.MyException;

import java.util.List;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/30.
 * @Version V 1.0.0
 * @Desc
 */
public interface CmCaseService {
    List<CmCase> findByType(int type) throws MyException;
}
