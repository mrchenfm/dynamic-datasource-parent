package com.cfm.datasource.common.enums;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: ConnectionPoolType
 * @Description: TODO(数据库连接池类型枚举:目前支持C3P0,DRUID,DBCP,HIKARI四种连接池，后续可以自己扩展)
 * @Author: fangming_chen
 * @Date: 2020/10/13 20:18
 */
public enum ConnectionPoolType {

    C3PO,
    DRUID,
    DBCP,
    HIKARI;
}
