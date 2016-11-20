package com.bc.common.dao;


import com.bc.core.Exception.MyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 复杂查询
 */
public interface BaseQueryDao {

    JdbcTemplate getJdbcTemplate();

    /**
     * 根据condition分页查询
     *
     * @param resultClass    结果集类型的class
     * @param pageable       spring的分页bean Pageable
     * @param sql
     * @param conditionModel 条件Condition
     * @param <T>
     * @return
     * @throws MyException
     */
    <T> Page<T> queryPageByConditionModel(Class<T> resultClass, Pageable pageable, String sql, Object conditionModel) throws MyException;

    <T> Page<T> queryPage(Class<T> resultClass, Pageable pageable, String sql, Object... params);

    <T> Page<Map<String, Object>> queryPageByConditionModel(Pageable pageable, String sql, Object conditionMode) throws MyException;

    <T> Page<Map<String, Object>> queryPage(Pageable pageable, String sql, Object... params);

    <T> List<T> queryListByConditionModel(Class<T> resultClass, String sql, Object conditionModel) throws MyException;

    <T> List<T> queryList(Class<T> resultClass, String sql, Object... params);

    <T> List<Map<String, Object>> queryListByConditionModel(String sql, Object conditionModel) throws MyException;

    <T> List<Map<String, Object>> queryList(String sql, Object... params);

    <T> T queryOneByConditionModel(Class<T> resultClass, String sql, Object conditionModel) throws MyException;

    <T> T queryOne(Class<T> resultClass, String sql, Object... params);

    Map<String, Object> queryOneByConditionModel(String sql, Object conditionModel) throws MyException;

    Map<String, Object> queryOne(String sql, Object... params);

    Number queryNumberByConditionMode(String sql, String numberKey, Object conditionModel) throws MyException;

    Number queryNumber(String sql, String numberKey, Object... params);

    Number queryCountByConditionMode(String sql, Object conditionModel) throws MyException;

    Number queryCount(String sql, Object... params);

    Integer execute(String sql, Object... params);

    Integer executeByConditionMode(String sql, Object conditionModel) throws MyException;

}
