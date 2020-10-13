package com.cfm.datasource.factory;

import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataSourcWapperFactory
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:25
 */
public interface DataSourceWrapperFactory {

    AbstractDataSourceWrapper buildDataSourceWrapper(String dbKey);

}
