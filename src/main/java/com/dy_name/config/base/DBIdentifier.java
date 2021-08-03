package com.dy_name.config.base;

/**
 * @author mzy
 * @date 2021/8/2 21:07
 * 数据库标识管理类
 */
public class DBIdentifier {

    private static ThreadLocal<String> JDBCURL = new ThreadLocal<>();

    public static String getJdbcUrl() {
        return JDBCURL.get();
    }

    public static void setJdbcUrl(String url) {
        JDBCURL.set(url);
    }
}
