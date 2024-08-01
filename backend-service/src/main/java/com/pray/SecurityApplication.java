package com.pray;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Rainy-Heights
 */
@EnableWebSecurity
@SpringBootApplication
@MapperScan(basePackages = "com.pray.mapper")
@Slf4j
public class SecurityApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SecurityApplication.class, args);
        Environment environment= applicationContext.getEnvironment();
        log.info("当前运行环境：Java版本：{}",environment.getProperty("java.version"));
    }
}

