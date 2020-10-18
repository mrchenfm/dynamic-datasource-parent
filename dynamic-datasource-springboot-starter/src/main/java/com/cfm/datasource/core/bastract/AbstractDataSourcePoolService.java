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
        if("cnr_core".equals(dbKey)){
            return new DataSourceBasicProperties("root","mrchen124578","jdbc:mysql://127.0.0.1:3306/cnr_core?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        }
        return new DataSourceBasicProperties("root","mrchen124578","jdbc:mysql://127.0.0.1:3306/test_db?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
    }
}
