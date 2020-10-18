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

    private DataSource dataSource = new DataSource();
    @Override
    public void afterPropertiesSet() throws Exception {

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
