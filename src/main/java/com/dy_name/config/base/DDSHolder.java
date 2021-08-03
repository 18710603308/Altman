package com.dy_name.config.base;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author mzy
 * @date 2021/8/2 21:13
 * 动态数据源管理器
 */
public class DDSHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DDSHolder.class);
    /**
     * 管理动态数据源列表。<工程编码，数据源>
     */
    private Map<String, DDSTimer> ddsMap = new HashMap<String, DDSTimer>();

    private DDSHolder() {

    }

    /*
     * 获取单例对象
     */
    public static DDSHolder instance() {
        return DDSHolderBuilder.instance;
    }

    /**
     * 添加动态数据源。
     *
     * @param projectCode 项目编码
     * @param dds dds
     */
    public synchronized void addDDS(String projectCode, DataSource dds) {

        DDSTimer ddst = new DDSTimer(dds);
        ddsMap.put(projectCode, ddst);
    }

    /**
     * 查询动态数据源
     *
     * @param projectCode 项目编码
     * @return dds
     */
    public synchronized DataSource getDDS(String projectCode) {

        if (ddsMap.containsKey(projectCode)) {
            DDSTimer ddst = ddsMap.get(projectCode);
            ddst.refreshTime();
            return ddst.getDds();
        }

        return null;
    }

    /**
     * 清除超时无人使用的数据源。
     */
    public synchronized void clearIdleDDS() {

        Iterator<Map.Entry<String, DDSTimer>> iter = ddsMap.entrySet().iterator();
        for (; iter.hasNext(); ) {

            Map.Entry<String, DDSTimer> entry = iter.next();
            if (entry.getValue().checkAndClose())
            {
                LOGGER.info(" >>>>>>>>>>>>>>> clear dataSource > key :{}", entry.getKey());
                iter.remove();
            }
        }
    }

    /**
     * 单例构件类
     * @author elon
     * @version 2018年2月26日
     */
    private static class DDSHolderBuilder {
        private static DDSHolder instance = new DDSHolder();
    }
}
