package com.bc.common.dao;


import com.bc.core.Exception.MyException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
public class BaseQueryDaoImpl implements BaseQueryDao {

    private static final String SQL_KEY = "#SQL#";
    private static final String COUNT_KEY = "RESULT_COUNT";
    private static final String QUERY_COUNT_SQL = " SELECT COUNT(1) AS " + COUNT_KEY + " FROM ( " + SQL_KEY + " ) QUEYR_COUNT";
    private static final String DEFAULT_WEHRE = " 1=1 ";
    private static final Pattern REGEX = Pattern.compile(":{1}\\w+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN = Pattern.compile("\\{[^{]*\\}");

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public <T> Page<T> queryPageByConditionModel(Class<T> resultClass, Pageable pageable, String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryPage(resultClass, pageable, sp.getSql(), sp.getParams().toArray());
    }

    @Override
    public <T> Page<T> queryPage(Class<T> resultClass, Pageable pageable, String sql, Object... params) {
        String newSql = new String(sql + getSortSql(pageable.getSort()));
        Long total = queryCount(newSql, params).longValue();
        List<T> data = queryList(resultClass, genPageSql(newSql, pageable), params);
        return new PageImpl<T>(data, pageable, total);
    }

    @Override
    public Page<Map<String, Object>> queryPageByConditionModel(Pageable pageable, String sql, Object conditionMode) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionMode);
        return queryPage(pageable, sp.getSql(), sp.getParams().toArray());
    }


    @Override
    public Page<Map<String, Object>> queryPage(Pageable pageable, String sql, Object... params) {
        String newSql = new String(sql + getSortSql(pageable.getSort()));
        Long total = queryCount(newSql, params).longValue();
        List<Map<String, Object>> data = queryList(genPageSql(newSql, pageable), params);
        return new PageImpl<Map<String, Object>>(data, pageable, total);
    }

    @Override
    public <T> List<T> queryListByConditionModel(Class<T> resultClass, String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryList(resultClass, sp.getSql(), sp.getParams().toArray());
    }


    @Override
    public <T> List<T> queryList(Class<T> resultClass, String sql, Object... params) {
        return jdbcTemplate.query(sql, rowMapper(resultClass), params);
    }

    @Override
    public List<Map<String, Object>> queryListByConditionModel(String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryList(sp.getSql(), sp.getParams().toArray());
    }

    @Override
    public List<Map<String, Object>> queryList(String sql, Object... params) {
        return jdbcTemplate.queryForList(sql, params);
    }

    @Override
    public <T> T queryOneByConditionModel(Class<T> resultClass, String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryOne(resultClass, sp.getSql(), sp.getParams().toArray());
    }


    @Override
    public <T> T queryOne(Class<T> resultClass, String sql, Object... params) {
        List<T> result = queryList(resultClass, sql, params);
        if (result == null || result.isEmpty())
            return null;
        return result.get(0);
    }

    @Override
    public Map<String, Object> queryOneByConditionModel(String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryOne(sp.getSql(), sp.getParams().toArray());
    }


    @Override
    public Map<String, Object> queryOne(String sql, Object... params) {
        return jdbcTemplate.queryForMap(sql, params);
    }

    @Override
    public Number queryNumberByConditionMode(String sql, String numberKey, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryNumber(sp.getSql(), numberKey, sp.getParams().toArray());
    }


    @Override
    public Number queryNumber(String sql, String numberKey, Object... params) {
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, params);
        if (map == null)
            return null;
        return (Number) map.get(numberKey);
    }

    @Override
    public Number queryCountByConditionMode(String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return queryCount(sp.getSql(), sp.getParams().toArray());
    }


    @Override
    public Number queryCount(String sql, Object... params) {
        String query_count = QUERY_COUNT_SQL.replace(SQL_KEY, sql);
        return queryNumber(query_count, COUNT_KEY, params);
    }

    @Override
    public Integer execute(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public Integer executeByConditionMode(String sql, Object conditionModel) throws MyException {
        SqlAndParams sp = generateSqlAndParams(sql, conditionModel);
        return execute(sp.getSql(), sp.getParams().toArray());
    }

    public String genPageSql(String sql, Pageable pageable) {
        StringBuffer pageSql = new StringBuffer(sql).append(" ");
        pageSql.append("limit ");
        pageSql.append(pageable.getOffset()).append(",").append(pageable.getPageSize());
        return pageSql.toString();
    }


    /**
     * 获取排序SQl
     *
     * @param sort
     * @return
     */
    private String getSortSql(Sort sort) {
        if (sort == null)
            return "";
        Iterator<Sort.Order> iterator = sort.iterator();
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            sb.append(" ").append(order.getProperty()).append(" ").append(order.getDirection());
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        if (sb.length() > 0)
            return sb.insert(0, " ORDER BY ").toString();
        return sb.toString();
    }


    /**
     * 生成  预编译 SQL 与参数
     *
     * @param sql            原始SQL
     * @param conditionModel 查询条件模型
     * @return
     * @throws MyException
     */
    private SqlAndParams generateSqlAndParams(String sql, Object conditionModel) throws MyException {
        String newSql = cleanNullAttributes(sql, conditionModel);
        Matcher matcher = REGEX.matcher(newSql);
        StringBuffer sb = new StringBuffer();
        SqlAndParams result = new SqlAndParams();
        while (matcher.find()) {
            String key = matcher.group();
            String pk = key.replaceFirst(":", "");
            Object value = getProperty(conditionModel, pk);
            if (value == null)
                throw new MyException("param " + key + " is null by SQL :" + sql);
            else {
                matcher.appendReplacement(sb, paramAdd(result.getParams(), value));
            }
        }
        matcher.appendTail(sb);
        result.setSql(sb.toString());
        return result;

    }

    /**
     * 清除条件模型中 为NULL 的查询条件
     *
     * @param sql
     * @param conditionModel
     * @return
     */
    private String cleanNullAttributes(String sql, Object conditionModel) {
        Matcher sqlMatcher = PATTERN.matcher(sql);
        StringBuffer sb = new StringBuffer();

        while (sqlMatcher.find()) {
            StringBuffer temp = new StringBuffer(sqlMatcher.group());
            temp.deleteCharAt(temp.length() - 1).deleteCharAt(0);
            Matcher conditionMatcher = REGEX.matcher(temp);

            while (conditionMatcher.find()) {
                String mapKey = conditionMatcher.group().replaceFirst(":", "");
                Object value = getProperty(conditionModel, mapKey);
                if (value == null || (value instanceof String && ((String) value).trim().length() == 0)) {
                    temp = new StringBuffer(DEFAULT_WEHRE);
                    break;
                }
            }
            sqlMatcher.appendReplacement(sb, temp.toString());
        }
        sqlMatcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 生成 SQL 查询所用到的参数 并生成相应的 预编译所用到的? (主要解决数组和集合参数 转化 SQL in 的问题)
     *
     * @param params
     * @param value
     * @return
     */
    private String paramAdd(List<Object> params, Object value) {
        int length = 0;
        if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            length = collection.size();
            params.addAll(collection);
        } else if (value.getClass().isArray()) {
            Object[] array = (Object[]) value;
            length = array.length;
            Collections.addAll(params, array);
        } else {
            length = 1;
            params.add(value);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append("?");
            if (i + 1 < length)
                sb.append(",");
        }
        return sb.toString();
    }


    /**
     * 通过反射获取属性
     *
     * @param obj
     * @param key
     * @return
     */
    private Object getProperty(Object obj, String key) {
        if (obj == null)
            return null;
        try {
            return PropertyUtils.getProperty(obj, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 行记录转换pojo
     *
     * @param cls
     * @return
     */
    private <T> RowMapper<T> rowMapper(final Class<T> cls) {
        return new RowMapper<T>() {

            private ConcurrentHashMap<String, Method> successCache;//不需要类型转换的
            private ConcurrentHashMap<String, PropertyDescriptor> converCache;//需要类型转换的

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rowNum == 0) {  //初始化mapping 需要的缓存
                    initCache(rs.getMetaData());
                }

                T object = null;
                try {
                    object = cls.newInstance();
                } catch (Exception e) {

                }
                if (object == null) {
                    return null;
                }

                for (Map.Entry<String, Method> entry : successCache.entrySet()) { //不需要转换的字段

                    setValue(entry.getValue(), object, getValue(rs, entry.getKey()));

                }

                for (PropertyDescriptor propertyDescriptor : converCache.values()) { //需要用到类型转换的字段
                    String name = propertyDescriptor.getName();
                    Object value = getValue(rs, name);
                    if (value != null) {
                        Class<?> trgetClass = propertyDescriptor.getPropertyType();
                        Method method = propertyDescriptor.getWriteMethod();
                        if (value.getClass() == trgetClass || value.getClass().getSuperclass() == trgetClass) {
                            setValue(method, object, value);
                            converCache.remove(name);
                            successCache.put(name, method);
                        } else {
                            setValue(method, object, value, trgetClass);
                        }
                    }
                }
                return object;
            }

            //从结果集中获取value
            private Object getValue(ResultSet rs, String key) {
                if (key != null) {
                    try {
                        return rs.getObject(key);
                    } catch (Exception e) {

                    }
                }
                return null;
            }

            //需要转换设置value
            private void setValue(Method method, Object trget, Object value, Class<?> trgetClass) {
                if (value != null) {
                    try {
                        method.invoke(trget, ConvertUtils.convert(value, trgetClass));
                    } catch (Exception e) {

                    }
                }
            }

            //需要转换设置value
            private void setValue(Method method, Object trget, Object value) {
                if (value != null) {
                    try {
                        method.invoke(trget, value);
                    } catch (Exception e) {

                    }
                }
            }


            //初始化mapping缓存
            private void initCache(ResultSetMetaData rmd) { //缓存mapping 以加快转换的速度

                converCache = new ConcurrentHashMap<String, PropertyDescriptor>();
                successCache = new ConcurrentHashMap<String, Method>();

                PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(cls);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                    if (!propertyDescriptor.getName().equalsIgnoreCase("class"))
                        converCache.put(propertyDescriptor.getName(), propertyDescriptor);
                }
                try {
                    for (int i = 1; i < rmd.getColumnCount(); i++) {
                        String name = rmd.getColumnLabel(i);
                        if (!converCache.containsKey(name)) {
                            converCache.remove(name);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    class SqlAndParams {
        private String sql;
        private List<Object> params;

        public SqlAndParams() {
            params = new ArrayList<Object>();
        }

        public SqlAndParams(List<Object> params) {
            this.params = params;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public List<Object> getParams() {
            return params;
        }

        public void setParams(List<Object> params) {
            this.params = params;
        }


    }

}
