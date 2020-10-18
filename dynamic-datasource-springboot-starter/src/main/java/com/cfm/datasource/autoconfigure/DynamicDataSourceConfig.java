package com.cfm.datasource.autoconfigure;

import com.cfm.datasource.DynamicDataSource;
import com.cfm.datasource.aop.DynamicDataSourceAspect;
import com.cfm.datasource.core.BasePoolProperties;
import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.core.DynamicProperties;

import com.cfm.datasource.core.bastract.AbstractDataSourcePoolService;
import com.cfm.datasource.factory.DataSourceWrapperFactory;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.util.concurrent.ConcurrentHashMap;

import static com.cfm.datasource.common.contants.DataSourceContants.DYNAMIC__DATASOURCE_PREFIX;
import static com.cfm.datasource.common.contants.DataSourceContants.SINGLE_DATASOURCE_PREFIX;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSourceConfig
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/17 15:50
 */
@Configuration
@EnableConfigurationProperties(DynamicProperties.class)
public class DynamicDataSourceConfig {

    @Value("${dynamic.datasource.username:}")
    private String username;

    /**
     * 普通数据源
     * @param dataSourcePoolService
     * @return
     */
    @Bean("datasource")
    @ConditionalOnProperty(prefix = SINGLE_DATASOURCE_PREFIX,value = "enabled",matchIfMissing = true)
    public DataSource basicDataSource(DataSourcePoolService dataSourcePoolService){

        return dataSourcePoolService.buildDataSource();
    }

    @Bean("dataSource")
    @ConditionalOnProperty(prefix = SINGLE_DATASOURCE_PREFIX,value = "enabled",matchIfMissing = true)
    public DataSource securityDataSource(DataSourceWrapperFactory dataSourceWrapperFactory
            ,DataSourcePoolService dataSourcePoolService){

        return buildDynamicDataSource(dataSourceWrapperFactory,dataSourcePoolService);
    }

    @Bean("dataSource")
    @ConditionalOnProperty(prefix = DYNAMIC__DATASOURCE_PREFIX,value = "enabled",matchIfMissing = true)
    public DataSource dynamicDataSource(DataSourceWrapperFactory dataSourceWrapperFactory
            ,DataSourcePoolService dataSourcePoolService){

        return buildDynamicDataSource(dataSourceWrapperFactory,dataSourcePoolService);
    }

    private DataSource buildDynamicDataSource(DataSourceWrapperFactory dataSourceWrapperFactory,
                                              DataSourcePoolService dataSourcePoolService) {

        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        ConcurrentHashMap<String, AbstractDataSourceWrapper> dataSourceWrappers = new ConcurrentHashMap<>();

        //获取数据库key，去创建数据源
        String dbKey = BasePoolProperties.getValue(username, null, null);

        AbstractDataSourceWrapper abstractDataSourceWrapper = dataSourceWrapperFactory.buildDataSourceWrapper(dbKey);
        dataSourceWrappers.put(dbKey,abstractDataSourceWrapper);
        dynamicDataSource.setDbWrapperFactory(dataSourceWrapperFactory);
        dynamicDataSource.setTargetDataSources(dataSourceWrappers);
        dynamicDataSource.setDefaultTargetDataSource(abstractDataSourceWrapper.getDataSource());

        return dynamicDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DYNAMIC__DATASOURCE_PREFIX, value = "enabled", matchIfMissing = false)
    public DynamicDataSourceAspect dynamicDataSourceAspect(){

        return  new DynamicDataSourceAspect();
    }



}
