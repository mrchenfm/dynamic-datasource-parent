package com.cfm.datasource.core.bastract;

import com.cfm.datasource.core.DataSourceBasicProperties;
import com.cfm.datasource.core.DataSourcePoolService;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: AbstractDataSourcePoolService
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:52
 */
public abstract class AbstractDataSourcePoolService implements DataSourcePoolService {
    protected DataSourceBasicProperties getSecurityDbConfig(String dbKey){
        return new DataSourceBasicProperties();
    }
}
