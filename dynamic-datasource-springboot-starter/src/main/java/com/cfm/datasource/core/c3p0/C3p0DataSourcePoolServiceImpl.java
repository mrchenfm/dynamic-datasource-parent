package com.cfm.datasource.core.c3p0;

import com.cfm.datasource.common.enums.ConnectionPoolType;
import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.core.bastract.AbstractDataSourcePoolService;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

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
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        return comboPooledDataSource;
    }
}
