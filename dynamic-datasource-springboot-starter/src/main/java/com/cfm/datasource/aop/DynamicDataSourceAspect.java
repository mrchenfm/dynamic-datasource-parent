package com.cfm.datasource.aop;

import com.cfm.datasource.DynamicDataSource;
import com.cfm.datasource.annotation.DataSource;
import com.cfm.datasource.core.DynamicDataSourceContextHolder;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSourceAspect
 * @Description: 动态数据源切面aop
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:06
 */
@Aspect
public class DynamicDataSourceAspect implements ApplicationContextAware {

    protected static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private ApplicationContext applicationContext;

    /**
     * 是否是动态数据源
     */
    private static ThreadLocal<Boolean> isDynamic = new ThreadLocal<Boolean>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 切换数据源
     * @param joinPoint
     */
    @Before("execution(* com.cfm..mapper..*.*(..))")
    public void switchDataSource(JoinPoint joinPoint){
        if(BooleanUtils.isTrue(isDynamic.get())){
            isDynamic.set(false);
            return;
        }

        DataSource dataSource = getDataSource(joinPoint);

        String targetDataSource =(dataSource == null || StringUtils.isBlank(dataSource.value()) ? getDefaultDataSource() : dataSource.value());

        DynamicDataSourceContextHolder.push(targetDataSource);

        logger.debug("Switch to {} data source.", targetDataSource);

    }

    private String getDefaultDataSource() {
        return  applicationContext.getBean(DynamicDataSource.class).getDefaultTargetDataSourceKey();
    }

    private DataSource getDataSource(JoinPoint joinPoint) {

        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //获取方法
        Method method = methodSignature.getMethod();

        //
        DataSource declaredAnnotation = method.getDeclaredAnnotation(DataSource.class);
        if(declaredAnnotation == null){
            declaredAnnotation = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
        }
        if(declaredAnnotation == null){
            Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
            if( !ObjectUtils.isEmpty(interfaces)){
                for(Class<?> interfaceClazz : interfaces){
                    if(interfaceClazz.getAnnotation(DataSource.class) != null){
                        return interfaceClazz.getAnnotation(DataSource.class);
                    }
                }
            }
        }
        return declaredAnnotation;
    }

    /**
     * 清空
     */
    @After("execution(* com.cfm..mapper..*.*(..))")
    public void clean() {
        DynamicDataSourceContextHolder.clear();
    }


    /**
     * 切换事务的数据源
     *
     * @param call
     * @throws Exception
     */
    @Before("execution(* com.cfm..service..*.*(..))")
    public void switchDataSourceOfTransaction(JoinPoint call) throws Exception {
        Class<?> targetClass = call.getTarget().getClass();
        Method definedMethod = getDefinedMethod(call, targetClass);
        if (definedMethod == null) {
            return;
        }
        if (definedMethod.isAnnotationPresent(DataSource.class) || targetClass.isAnnotationPresent(DataSource.class)) {
            switchDataSource(call);
        }
    }

    /**
     * 获取定义的方法
     *
     * @param call
     * @param targetClass
     * @return
     * @throws NoSuchMethodException
     */
    private Method getDefinedMethod(JoinPoint call, Class<?> targetClass) {
        // 先获取接口的方法
        MethodSignature methodSignature = (MethodSignature) call.getSignature();
        Method declaredMethod = methodSignature.getMethod();
        // 再获取方法的具体实现
        try {
            Method definedMethod = targetClass.getMethod(declaredMethod.getName(), declaredMethod.getParameterTypes());
            return definedMethod;
        } catch (NoSuchMethodException e) {
            logger.error("方法{}的修饰符请使用public, 否则数据源无法切换.", declaredMethod.getName());
            return null;
        }
    }
}
