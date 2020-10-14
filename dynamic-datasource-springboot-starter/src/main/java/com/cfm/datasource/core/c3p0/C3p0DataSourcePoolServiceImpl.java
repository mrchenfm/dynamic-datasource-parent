package com.cfm.datasource.core.c3p0;

import com.cfm.datasource.common.enums.ConnectionPoolType;
import com.cfm.datasource.core.BasePoolProperties;
import com.cfm.datasource.core.DataSourceBasicProperties;
import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.core.bastract.AbstractDataSourcePoolService;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: C3p0DataSourcePoolServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:33
 */
@Component
public class C3p0DataSourcePoolServiceImpl extends AbstractDataSourcePoolService {

    @Autowired
    private C3p0Properties c3p0Properties;

    @Override
    public String getType() {
        return ConnectionPoolType.C3PO.name();
    }

    @Override
    public DataSource buildDataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        return comboPooledDataSource;
    }

    @Override
    public DataSource buildSecurityDataSource(String dbKey) {
        DataSourceBasicProperties dbSecurityInfo = super.getSecurityDbConfig(dbKey);
        if(!ObjectUtils.isEmpty(dbSecurityInfo)) {
            ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setUser(dbSecurityInfo.getUserName());
            comboPooledDataSource.setPassword(dbSecurityInfo.getPassword());
            comboPooledDataSource.setJdbcUrl(dbSecurityInfo.getUrl());
            /**
             * 设置公共属性
             */
            setCommonProperties(comboPooledDataSource);
            // 记录数据库及其jdbcUrl.
            return comboPooledDataSource;
        }
        throw new RuntimeException("从配置中心未获取DB:" + dbKey + "的账号密码等核心信息.");
    }

    private void setCommonProperties(ComboPooledDataSource dataSource) {

        try {
            dataSource.setDriverClass(BasePoolProperties.getValue(c3p0Properties.driverClass, BasePoolProperties.DEFAULT_DRIVER_CLASS, null));
            dataSource.setInitialPoolSize(BasePoolProperties.getValue(c3p0Properties.initialSize, 1, null));
            dataSource.setMaxPoolSize(BasePoolProperties.getValue(c3p0Properties.maxActive, 20, null));
            dataSource.setMinPoolSize(BasePoolProperties.getValue(c3p0Properties.minIdle, 3, null));
            dataSource.setMaxIdleTime(BasePoolProperties.getValue(c3p0Properties.maxIdleTime, 60));
            dataSource.setAcquireIncrement(BasePoolProperties.getValue(c3p0Properties.acquireIncrement, 3));
            dataSource.setAcquireRetryAttempts(BasePoolProperties.getValue(c3p0Properties.acquireRetryAttempts, 10));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

    }
}
