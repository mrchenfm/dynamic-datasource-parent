package com.cfm.datasource.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: BasePoolProperties
 * @Description: 连接池的公共配置
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:42
 */

public class BasePoolProperties {

    public static String DEFAULT_DRIVER_CLASS = "com.mysql.jdbc.Driver";

    @Value("${dynamic.datasource.username:}")
    public String username;

    @Value("${dynamic.datasource.password:}")
    public String password;

    @Value("${dynamic.datasource.url:}")
    public String url;

    @Value("${dynamic.datasource.driver-class:}")
    public String driverClass;

    @Value("${dynamic.datasource.initialSize:}")
    public Integer initialSize;

    @Value("${dynamic.datasource.maxIdle:}")
    public Integer maxIdle;

    @Value("${dynamic.datasource.minIdle:}")
    public Integer minIdle;

    @Value("${dynamic.datasource.maxActive:}")
    public Integer maxActive;

    @Value("${dynamic.datasource.maxWait:}")
    public Long maxWait;

    /**
     * 获取公共配置：有优先级的获取
     * @param <T>
     * @return
     */
    public static <T> T getValue(T highPriorityKey, T defaultValue, T... lowPriorityKey){
        if(highPriorityKey != null && !ObjectUtils.isEmpty(highPriorityKey)){
            return highPriorityKey;
        }
        if(lowPriorityKey.length > 0 && lowPriorityKey[0] != null && !ObjectUtils.isEmpty(lowPriorityKey[0])){
            return lowPriorityKey[0];
        }

        return defaultValue;
    }


}
