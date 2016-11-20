package com.bc.core.util;

import java.sql.*;


/**
 * 连接工具类
 * <p>
 * ConnUtils类声明为final类说明此类不可以被继承
 *
 * @author jiqinlin
 */
public class JDBCUtils {


//  private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=gbk&autoReconnect=true&failOverReadOnly=false";
    private static String user = "root";
    private static String password = "root";

    /**
     * 说明要访问此类只能通过static或单例模式
     */
    private JDBCUtils() {    }

    // 注册驱动 (只做一次)
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 获取Connection对象
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

}
