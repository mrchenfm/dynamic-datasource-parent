package com.cfm.datasource.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: DynamicProperties
 * @Description: DynamicProperties
 * @Author: fangming_chen
 * @Date: 2020/10/17 15:51
 */
@Getter
@Slf4j
@ConfigurationProperties(prefix = "dynamic")
public class DynamicProperties implements InitializingBean {

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    @Autowired
    private Environment environment;

    private DataSource datasource = new DataSource();
    @Override
    public void afterPropertiesSet() throws Exception {
        //datasource
        if (this.datasource.username == null) {
            this.datasource.username = environment.getProperty("dynamic.datasource.username");
        }
        if (this.datasource.password == null) {
            this.datasource.password = environment.getProperty("dynamic.datasource.password");
        }
        if (this.datasource.url == null) {
            this.datasource.url = environment.getProperty("dynamic.datasource.url");
        }
        if (this.datasource.driver == null) {
            this.datasource.driver = environment.getProperty("dynamic.datasource.driver-class-name");
            if (this.datasource.driver == null){
                this.datasource.driver = DRIVER_CLASS;
            }
        }
        if (this.datasource.initialSize == 0) {
            if (environment.getProperty("dynamic.datasource.druid.initialSize") != null) {
                this.datasource.initialSize = Integer.parseInt(environment.getProperty("dynamic.datasource.druid.initialSize"));
            } else if (environment.getProperty("dynamic.datasource.initialSize") != null) {
                this.datasource.initialSize = Integer.parseInt(environment.getProperty("dynamic.datasource.initialSize"));
            } else {
                this.datasource.initialSize = 1;
            }
        }
        if (this.datasource.minIdle == 0) {
            if (environment.getProperty("dynamic.datasource.druid.minIdle") != null) {
                this.datasource.minIdle = Integer.parseInt(environment.getProperty("dynamic.datasource.druid.minIdle"));
            } else if (environment.getProperty("dynamic.datasource.minIdle") != null) {
                this.datasource.minIdle = Integer.parseInt(environment.getProperty("dynamic.datasource.minIdle"));
            } else {
                this.datasource.minIdle = 1;
            }
        }
        if (this.datasource.maxActive == 0) {
            if (environment.getProperty("dynamic.datasource.druid.maxActive") != null) {
                this.datasource.maxActive = Integer.parseInt(environment.getProperty("spring.datasource.druid.maxActive"));
            } else if (environment.getProperty("dynamic.datasource.maxActive") != null) {
                this.datasource.maxActive = Integer.parseInt(environment.getProperty("dynamic.datasource.maxActive"));
            } else {
                this.datasource.maxActive = 20;
            }
        }
    }

    @Getter
    @Setter
    @ToString(exclude = {"username", "password"})
    public class DataSource{

        private String username;

        private String password;

        private String url;

        private String driver;

        private int initialSize;

        private int minIdle;

        private int maxActive;

        private int maxIdle;

        private long maxWait;

        private Druid druid = new Druid();

        private Dbcp dbcp = new Dbcp();
    }

    @Getter
    @Setter
    public class Dbcp extends Basic {

        private String connectionProperties = "";

        @Override
        public String toString() {
            return "Dbcp [connectionProperties=" + connectionProperties + ", timeBetweenEvictionRunsMillis=" + timeBetweenEvictionRunsMillis + ", minEvictableIdleTimeMillis="
                    + minEvictableIdleTimeMillis + ", validationQuery=" + validationQuery + ", testWhileIdle=" + testWhileIdle + ", testOnBorrow=" + testOnBorrow
                    + ", testOnReturn=" + testOnReturn + "]";
        }

    }

    @Getter
    @Setter
    public class Druid extends Basic {

        private String filters; //"stat,wall,slf4j"

        private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";

        @Override
        public String toString() {
            return "Druid [filters=" + filters + ", connectionProperties=" + connectionProperties + ", timeBetweenEvictionRunsMillis=" + timeBetweenEvictionRunsMillis
                    + ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis + ", validationQuery=" + validationQuery + ", testWhileIdle=" + testWhileIdle + ", testOnBorrow="
                    + testOnBorrow + ", testOnReturn=" + testOnReturn + "]";
        }

    }

    @Getter
    @Setter
    @ToString
    public class Basic {

        protected long timeBetweenEvictionRunsMillis = 60000L;

        protected long minEvictableIdleTimeMillis = 300000L;

        protected String validationQuery = "SELECT 1";

        protected boolean testWhileIdle = true;

        protected boolean testOnBorrow = false;

        protected boolean testOnReturn = false;
    }

}
