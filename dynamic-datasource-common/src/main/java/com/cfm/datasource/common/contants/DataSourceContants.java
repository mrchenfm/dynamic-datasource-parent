package com.cfm.datasource.common.contants;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataSourceContants
 * @Description: 数据源启动器的公共常量
 * @Author: fangming_chen
 * @Date: 2020/10/13 20:23
 */
public class DataSourceContants {

    /**
     * 动态数据源开启开关，默认是关闭的
     */
    public static final String DYNAMIC__DATASOURCE_PREFIX = "dynamic.datasource.trends";

    /**
     * 单数据源开启开关，默认是开启的
     */
    public static final String SINGLE_DATASOURCE_PREFIX = "dynamic.datasource.single";

    /**
     * 数据库连接池类型：c3p0,druid,dbcp,HIKARI
     * dynamic.datasource.type.c3p0 = true
     * dynamic.datasource.type.druid = true
     */
    public static final String DYNAMIC_DB_TYPE = "dynamic.datasource.type";
}
