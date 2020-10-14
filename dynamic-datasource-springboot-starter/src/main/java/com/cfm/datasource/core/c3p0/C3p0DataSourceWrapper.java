package com.cfm.datasource.core.c3p0;

import com.cfm.datasource.core.DataSourcePoolService;
import com.cfm.datasource.wrapper.AbstractDataSourceWrapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: C3p0DataSourceWrapper
 * @Description: 提供数据库连接池创建与销毁的包装器.
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:41
 */
public class C3p0DataSourceWrapper extends AbstractDataSourceWrapper {

    /**
     * 构造数据库连接池
     * @param dataSourcePoolService
     * @param dbkey
     */
    public C3p0DataSourceWrapper(DataSourcePoolService dataSourcePoolService,String dbkey){
        super(dataSourcePoolService,dbkey);
    }

    @Override
    protected void reCreateDataSource() {
        try {
            DataSource tmpdataSource = dataSource;
            this.doCreateDataSource();
            ((ComboPooledDataSource) tmpdataSource).close();
            log.info("[c3p0连接池] 销毁{}连接池SUCCESS.", super.dbKey);
        } catch (Throwable e) {
            log.warn("[c3p0连接池] 销毁{}连接池Failure.", super.dbKey, e);
        }
    }
}