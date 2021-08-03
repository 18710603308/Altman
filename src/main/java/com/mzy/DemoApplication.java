package com.mzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:50
 * @desc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.altman","com.dy_name"})
@MapperScan(basePackages = {"com.altman.mapper","com.dy_name.mapper"})
@EnableScheduling
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }
}
