package com.bc.core.util;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Copyright 2012-2016 duenboa 版权所有
 * @Author Created by Administrator on 2016/10/26.
 * @Version V 1.0.0
 * @Desc
 */
public class WebApplicationContextUtil {

    private static WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();

    static {
        System.out.println("=============================================================== WebApplicationContext 初始化完成! <<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
        System.out.println("=============================================================== WebApplicationContextUtil初始化完成...<<<<<<<<<<<<<<<<<<<<<<<<< ");
    }

    /**
     * 根据类型返回bean
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    /**
     * 根据名称返回bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

}
