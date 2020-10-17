package com.cfm.datasource.autoconfigure;

import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.core.c3p0.C3p0DataSourcePoolServiceImpl;
import com.cfm.datasource.core.c3p0.C3p0Properties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import static com.cfm.datasource.common.contants.DataSourceContants.DYNAMIC_DB_TYPE;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: C3p0DataSourceConfig
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/17 15:36
 */
@Configuration
@ComponentScan("com.cfm.datasource")
@ConditionalOnClass(ComboPooledDataSource.class)
@ConditionalOnProperty(prefix = DYNAMIC_DB_TYPE,value = "c3p0",matchIfMissing = true)
public class C3p0DataSourceConfig {

    @Bean
    public C3p0Properties c3p0Properties(){
        return new C3p0Properties();
    }

    @Bean
    public DataSourcePoolService dataSourcePoolService(){
        return  new C3p0DataSourcePoolServiceImpl();
    }
}
