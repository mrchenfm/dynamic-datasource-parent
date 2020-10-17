package com.cfm.datasource;

import com.cfm.datasource.factory.DataSourceWrapperFactory;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    private ConcurrentHashMap<String, AbstractDataSourceWrapper> targetDataSources;

    private DataSourceWrapperFactory dbWrapperFactory;

    public void setTargetDataSources(ConcurrentHashMap<String, AbstractDataSourceWrapper> targetDataSources) {

        // 从AbstractDataSourceWrapper获取DataSource.
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
        for (Iterator<String> iterator = targetDataSources.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            DataSource dataSource = targetDataSources.get(key).getDataSource();
            dataSourceMap.put(key, dataSource);
        }
        super.setTargetDataSources(dataSourceMap);

        this.targetDataSources = targetDataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }


    @Override
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        super.setDataSourceLookup(dataSourceLookup);
    }

    public String getDefaultTargetDataSourceKey() {
        return defaultTargetDataSourceKey;
    }

    public void setDbWrapperFactory(DataSourceWrapperFactory wrapperFactory) {
        this.dbWrapperFactory = wrapperFactory;
    }
}
