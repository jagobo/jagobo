package com.bc.core.Exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义异常
 */
public class MyException extends Exception {
    private String message;
    private Object data;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Object obj) {
        super();
        this.message = message;
        this.data = obj;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = StringUtils.trimToEmpty(message);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
