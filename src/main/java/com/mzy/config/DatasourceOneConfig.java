package com.mzy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:29
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.mzy.mapper.one", sqlSessionFactoryRef = "oneSqlSessionFactory")
public class DatasourceOneConfig {

    @Bean(name = "oneDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oneSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean(name = "oneTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("oneDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oneSqlSessionTemplate")
    public SqlSessionTemplate sessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
