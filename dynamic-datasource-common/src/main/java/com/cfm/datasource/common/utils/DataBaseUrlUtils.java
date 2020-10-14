package com.cfm.datasource.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataBaseUrlUtils
 * @Description: 处理解析jdbc url的工具类
 * @Author: fangming_chen
 * @Date: 2020/10/13 20:22
 */
public class DataBaseUrlUtils {

    /**
     * 获取数据库的域名和端口
     * @param jdbcUrl
     * @return
     */
    public static String getDomainAndPort(String jdbcUrl){
        return StringUtils.split(jdbcUrl,"//")[0];
    }

    /**
     * 获取数据库的域名
     * @param jdbcUrl
     * @return
     */
    public static String getDomain(String jdbcUrl){
        String domainAndPort = getDomainAndPort(jdbcUrl);

        return StringUtils.split(domainAndPort,":")[0];
    }

    public static String doMainToIp(String domain) throws UnknownHostException {

        return InetAddress.getByName(domain).getHostAddress();
    }
}
