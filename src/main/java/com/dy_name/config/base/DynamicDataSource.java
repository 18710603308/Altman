package com.dy_name.config.base;


import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author mzy
 * @date 2021/8/2 20:53
 * 定义动态数据源派生类.从基础的DataSource
 */
public class DynamicDataSource extends DataSource {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * 连接不同数据库
     */
    @Override
    public Connection getConnection() throws SQLException {
        String jdbcUrl = DBIdentifier.getJdbcUrl();
        if(StringUtils.isEmpty(jdbcUrl)){
            jdbcUrl = "default";
        }
        // 获取数据源
        DataSource dds = DDSHolder.instance().getDDS(jdbcUrl);
        // 数据源不存在则创建
        if(dds == null){
            try {
                dds = initDDS(jdbcUrl);
                DDSHolder.instance().addDDS(jdbcUrl, dds);
            } catch (IllegalAccessException e) {
                logger.error("Init data source fail. projectCode:" + jdbcUrl);
                return null;
            }
        }
        return dds.getConnection();
    }

    private DataSource initDDS(String url) throws IllegalAccessException {
        final DataSource source = new DataSource();
        // 2、复制PoolConfiguration的属性
        PoolProperties property = new PoolProperties();
        Field[] pfields = PoolProperties.class.getDeclaredFields();
        for (Field f : pfields) {
            f.setAccessible(true);
            Object value = f.get(this.getPoolProperties());
            try
            {
                f.set(property, value);
            }
            catch (Exception e)
            {
                //有一些static final的属性不能修改。忽略。
                logger.info("Set value fail. attr name:" + f.getName());
                continue;
            }
        }
        source.setPoolProperties(property);
        if(!"default".equals(url)){
            source.setUrl(url);
        }
        return source;
    }
}
