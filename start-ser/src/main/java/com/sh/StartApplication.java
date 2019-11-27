package com.sh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/9 17:13
 */
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.sh.**"})
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StartApplication.class);
        application.run(args);
    }
}
