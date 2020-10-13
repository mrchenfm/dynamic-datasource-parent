package com.cfm.datasource.annotation;

import java.lang.annotation.*;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataSource
 * @Description: TODO(动态数据源注解)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:02
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * 默认为空，就用默认的数据源
     * @return
     */
    String value() default "";
}
