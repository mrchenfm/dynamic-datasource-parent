package com.cfm.datasource.core;

import javax.sql.DataSource;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataSourcePoolService
 * @Description: 数据源连接创建接口
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:32
 */
public interface DataSourcePoolService {

    /**
     * 获取连接池类型
     * @return
     */
    String getType();

    /**
     * 非脱敏数据源：指定账号密码 未开发
     * @return
     */
    DataSource buildDataSource();

    /**
     *脱敏数据源，不指定密码
     * @param dbKey
     * @return
     */
    DataSource buildSecurityDataSource(String dbKey);
}
