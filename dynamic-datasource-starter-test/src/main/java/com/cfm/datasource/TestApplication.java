package com.cfm.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: TestApplication
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/18 10:17
 */
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan(value = {"com.cfm.datasource.mapper"})
@ComponentScan(value = {"com.cfm.datasource"})
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class
})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }
}
