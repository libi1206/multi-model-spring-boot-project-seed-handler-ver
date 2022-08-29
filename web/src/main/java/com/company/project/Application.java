package com.company.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author :Libi
 * @version :1.0
 * @date :2020-06-07 22:33
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = {"com.company.project.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}