package com.bc.cas.controller;

import com.alibaba.fastjson.JSONArray;
import com.bc.cas.model.entity.CmCase;
import com.bc.cas.service.impl.CmCaseServiceImpl;
import com.bc.core.util.WebApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc
 */
@Controller
@RequestMapping("/case")
public class CmCaseController {

    private static final Logger logger = LoggerFactory.getLogger(CmCaseController.class);

    @Autowired
    private CmCaseServiceImpl cmCaseService;


    //http://localhost:8080/bc-case/case/query/getListByType.do?type=1001
    @RequestMapping("query/getListByType")
    public void getListByType(int type, HttpServletResponse response) {
        try {
            DataSource dataSource = (DataSource) WebApplicationContextUtil.getBean("dataSource");
            List<CmCase> caseList = cmCaseService.findByType(type);
            String s = JSONArray.toJSONString(caseList);
            PrintWriter writer = null;
            writer = response.getWriter();
            writer.write(s);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
