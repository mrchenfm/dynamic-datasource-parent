package com.cfm.datasource.factory;

import com.cfm.datasource.core.DataSourcePoolService;
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
        return null;
    }
}
