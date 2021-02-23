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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:41
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.mzy.mapper.two", sqlSessionFactoryRef = "twoSqlSessionFactory")
public class DatasourceTwoConfig {

    @Primary
    @Bean(name = "twoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("twoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "twoTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("twoDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "twoSqlSessionTemplate")
    public SqlSessionTemplate sessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
