package com.cfm.datasource.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSourceAspect
 * @Description: TODO(动态数据源切面aop)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:06
 */
@Aspect
public class DynamicDataSourceAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
