package com.cfm.datasource.core;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DataSourceBasicProperties
 * @Description: 数据库连接的主要参数：用户名，密码，url
 * @Author: fangming_chen
 * @Date: 2020/10/14 20:52
 */
public class DataSourceBasicProperties {

    private String userName;

    private String password;

    private String url;

    public DataSourceBasicProperties() {
    }

    public DataSourceBasicProperties(String userName, String password, String url) {
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
