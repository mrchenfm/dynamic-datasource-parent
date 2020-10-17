package com.cfm.datasource;

import com.cfm.datasource.core.DynamicDataSourceContextHolder;
import com.cfm.datasource.factory.DataSourceWrapperFactory;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.util.Assert;

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

    /**
     * 设置目标数据源
     * @param targetDataSources
     */
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
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        if(defaultTargetDataSource instanceof String){
            this.defaultTargetDataSourceKey = (String) defaultTargetDataSource;
        }else if(defaultTargetDataSource instanceof DataSource){
            for(Map.Entry<String,AbstractDataSourceWrapper> entry : this.targetDataSources.entrySet()){
                if(defaultTargetDataSource == entry.getValue().getDataSource()){
                    this.defaultTargetDataSourceKey = entry.getKey();
                    return;
                }
            }
        }
        throw  new RuntimeException("默认的数据源必须被缓存在targetDataSource");
    }

    @Override
    public void setLenientFallback(boolean lenientFallback) {
        super.setLenientFallback(lenientFallback);
    }

    /**
     * 确定目标数据源
     * @return
     */
    @Override
    protected DataSource determineTargetDataSource() {
        Assert.isNull(targetDataSources,"目标数据源为空");
        Object currentLookupKey = determineCurrentLookupKey();

        /**
         * 目标数据源的key是否为空，为空的话获取默认的值
         */
        currentLookupKey = currentLookupKey == null ? this.defaultTargetDataSourceKey : currentLookupKey;

        try{
            return this.getRealDataSource(currentLookupKey);
        }catch (Throwable e){
            throw new IllegalStateException("不能确定数据源，key = [" + currentLookupKey + "]", e);
        }
    }

    /**
     * 获取正真的数据源
     * @param currentLookupKey
     * @return
     */
    private DataSource getRealDataSource(Object currentLookupKey) {

        AbstractDataSourceWrapper abstractDataSourceWrapper = targetDataSources.get(currentLookupKey);
        //目标数据源不为空返回该数据源
        if(abstractDataSourceWrapper != null){
            return abstractDataSourceWrapper.getDataSource();
        }
        //为空，根据key创建一个新的数据源
        return this.createDataSource(currentLookupKey);
    }

    /**
     * 创建一个新的数据源
     * @param currentLookupKey
     * @return
     */
    private DataSource createDataSource(Object currentLookupKey) {
        // 进行二次判断，避免并发创建多个连接池.
        AbstractDataSourceWrapper abstractDataSourceWrapper = targetDataSources.get(currentLookupKey);
        if(abstractDataSourceWrapper != null){
            return abstractDataSourceWrapper.getDataSource();
        }
        //如果还是没有
        AbstractDataSourceWrapper targetDataSourceWrapper = dbWrapperFactory.buildDataSourceWrapper((String) currentLookupKey);

        targetDataSources.put((String) currentLookupKey,targetDataSourceWrapper);
        return targetDataSourceWrapper.getDataSource();
    }

    /**
     * 确定当前数据源的key
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.peek();
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
