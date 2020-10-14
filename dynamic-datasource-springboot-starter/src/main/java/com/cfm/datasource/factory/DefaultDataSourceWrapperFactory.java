package com.cfm.datasource.factory;

import com.cfm.datasource.common.enums.ConnectionPoolType;
import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.core.c3p0.C3p0DataSourceWrapper;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DefaultDataSourceWrapperFactory
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:30
 */
public class DefaultDataSourceWrapperFactory implements DataSourceWrapperFactory {

    @Resource
    private DataSourcePoolService dataSourcePoolService;

    @Override
    public AbstractDataSourceWrapper buildDataSourceWrapper(String dbKey) {

        if(ConnectionPoolType.C3PO.name().equals(dataSourcePoolService.getType())){
            return new C3p0DataSourceWrapper(dataSourcePoolService,dbKey);
        }
        // TODO 其他连接池的dataSource构建
        return null;
    }
}
