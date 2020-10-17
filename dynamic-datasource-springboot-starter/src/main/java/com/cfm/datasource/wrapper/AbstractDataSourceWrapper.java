package com.cfm.datasource.wrapper;

import com.cfm.datasource.common.utils.DataBaseUrlUtils;
import com.cfm.datasource.core.DataSourcePoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @version v1.0
 * @ProjectName: dynamic-datasource-parent
 * @ClassName: AbstractDataSourceWrapper
 * @Description: 数据源创建、销毁包装类
 * @Author: fangming_chen
 * @Date: 2020/10/13 21:27
 */
public abstract class AbstractDataSourceWrapper {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 数据源连接创建接口
     */
    protected final DataSourcePoolService dataSourcePoolService;

    /**
     * 数据库名称
     */
    protected String dbKey;

    /**
     * 定时任务执行器
     */
    protected ScheduledExecutorService executorService;

    protected DataSource dataSource;

    public DataSource getDataSource() {
        return this.dataSource;
    }


    public AbstractDataSourceWrapper(DataSourcePoolService dataSourcePoolService, String dbkey) {
        this.dataSourcePoolService = dataSourcePoolService;
        this.dbKey = dbkey;
        //初始化
        init();
    }

    private void init(){
        this.doCreateDataSource();
        this.executorService = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2);
        this.executorService.scheduleAtFixedRate(new DataSourceDomainTask(),60 * 1000,10 * 1000, TimeUnit.MILLISECONDS);

    }

    protected void doCreateDataSource(){
        this.dataSource = this.dataSourcePoolService.buildSecurityDataSource(this.dbKey);
        log.info("【动态数据源】创建{}数据库连接成功！",dbKey);
    }


    /**
     * 重建数据源
     */
    protected abstract void reCreateDataSource();

    class DataSourceDomainTask extends TimerTask{
        String lastIp = null;

        @Override
        public void run() {
            try{
                //获取域名
                String domain = DataBaseUrlUtils.getDomain(dbKey);
                //根据域名获取ip
                String currentIp = DataBaseUrlUtils.doMainToIp(domain);
                log.debug("[数据源DNS] 数据库: {}, 域名: {}, ip: {}, lastIp: {}", dbKey, domain, currentIp, lastIp);
                if(this.lastIp == null){
                    lastIp = currentIp;
                    return;
                }

                if(!this.lastIp.equals(currentIp)){
                    log.info("[数据源DNS] 数据库: {} 域名解析发生变更, 当前域名: {}, ip: {}, lastIp: {}", dbKey, domain, currentIp, lastIp);
                    this.lastIp = currentIp;
                    reCreateDataSource();
                }
            }catch (Exception e){
                log.error("[数据源DNS]");
            }
        }
    }

}
