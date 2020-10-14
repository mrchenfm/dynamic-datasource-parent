package com.cfm.datasource.core.c3p0;

import com.cfm.datasource.core.BasePoolProperties;
import org.springframework.beans.factory.annotation.Value;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: C3p0Properties
 * @Description: C3P0连接池的配置
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:41
 */
public class C3p0Properties extends BasePoolProperties {

    /**
     *其实这里就是dynamic.datasource.maxWait的值，但是考虑到这里单位是秒，就不采用那个值.
     */
    @Value("${dynamic.datasource.c3p0.maxIdleTime}")
    public Integer maxIdleTime;

    /**
     * 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
     */
    @Value("${dynamic.datasource.c3p0.acquireIncrement}")
    public Integer acquireIncrement;

    /**
     * 失败后重复尝试的次数
     */
    @Value("${dynamic.datasource.c3p0.acquireRetryAttempts}")
    public Integer acquireRetryAttempts;

}
