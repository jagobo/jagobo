package com.bc.cas.manager;

import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/12/11.
 * @Version V 1.0.0
 * @Desc 索引管理
 */
@Component
public class IndexManager {

    private static final Logger logger = LoggerFactory.getLogger(IndexManager.class);

    static FSDirectory directory = IndexUtil.dir;


    public static void deleteIndex() {


    }


}
