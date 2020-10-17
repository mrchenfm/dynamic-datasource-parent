package com.cfm.datasource.core;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicDataSourceContextHolder
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/14 21:22
 */
public final class DynamicDataSourceContextHolder {

    /**
     *
     */
    private static final ThreadLocal<Deque<String>> LOOKUP_KEY_HOLDER = new NamedThreadLocal<Deque<String>>("dynamic-datasource") {
        @Override
        protected Deque<String> initialValue() {
            return new ArrayDeque<>();
        }
    };


    private DynamicDataSourceContextHolder(){

    }

    /**
     * 获取当前数据源
     * @return 数据源名称
     */
    public static String peek(){

        return LOOKUP_KEY_HOLDER.get().peek();
    }

    /**
     *设置当前线程数据源
     * @param ds
     */
    public static void push(String ds){

        LOOKUP_KEY_HOLDER.get().push(StringUtils.isEmpty(ds) ? "" : ds);
    }

    /**
     * 清空数据源
     */
    public static void poll(){
        Deque<String> deque = LOOKUP_KEY_HOLDER.get();
        deque.poll();
        if(deque.isEmpty()){
            LOOKUP_KEY_HOLDER.remove();
        }
    }

    /**
     * 强制清空本地线程
     * 防止内存泄漏，如手动调用了push可调用此方法确保清除
     */
    public static void clear(){

        LOOKUP_KEY_HOLDER.remove();
    }
}

