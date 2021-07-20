package com.mzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Jack Miao
 * @date 2021/1/11 20:50
 * @desc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.altman"})
@MapperScan(basePackages = {"com.altman.mapper"})
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }
}
