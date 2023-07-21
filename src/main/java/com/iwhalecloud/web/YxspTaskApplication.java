package com.iwhalecloud.web;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主程序入口
 */
@SpringBootApplication
@MapperScan({"com.iwhalecloud.web.dao.mapper"})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@Slf4j
public class YxspTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxspTaskApplication.class, args);
        log.info("YxspTaskApplication start!");
    }

}
