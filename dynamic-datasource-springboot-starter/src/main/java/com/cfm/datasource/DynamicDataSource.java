package com.cfm.datasource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSource
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:21
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements InitializingBean {

    private String defaultTargetDataSourceKey;

    private ConcurrentHashMap<String,AbstractRoutingDataSource> targetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
