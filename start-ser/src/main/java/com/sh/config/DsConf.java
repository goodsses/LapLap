package com.sh.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.sh.config.prop.DsProp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

import com.sh.util.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/10 13:33
 */
@Configuration
@EnableConfigurationProperties({DsProp.class})
@Slf4j
public class DsConf {

    private DsProp dsProp;

    @Autowired
    public void setDsProp(DsProp dsProp) {
        this.dsProp = dsProp;
    }

    @Bean(name = "myDataSource")
    @Qualifier("myDataSource")
    @Primary
    public DataSource getMyDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        if (StringUtils.isNotBlank(dsProp.getDriverClassName())) {
            dataSource.setDriverClassName(dsProp.getDriverClassName());
        }
        if (StringUtils.isNotBlank(dsProp.getDbType())) {
            dataSource.setDbType(dsProp.getDbType());
        }
        if (StringUtils.isNotBlank(dsProp.getUrl())) {
            dataSource.setUrl(dsProp.getUrl());
        }
        if (StringUtils.isNotBlank(dsProp.getUsername())) {
            dataSource.setUsername(dsProp.getUsername());
        }
        if (StringUtils.isNotBlank(dsProp.getPassword())) {
            dataSource.setPassword(dsProp.getPassword());
        }
        if (StringUtils.isNotBlank(dsProp.getValidationQuery())) {
            dataSource.setValidationQuery(dsProp.getValidationQuery());
        }
        if (null != dsProp.getTestOnBorrow()) {
            dataSource.setTestOnBorrow(dsProp.getTestOnBorrow());
        }
        if (null != dsProp.getInitialSize()) {
            dataSource.setInitialSize(dsProp.getInitialSize());
        }
        if (null != dsProp.getMinIdle()) {
            dataSource.setMinIdle(dsProp.getMinIdle());
        }
        if (null != dsProp.getMaxActive()) {
            dataSource.setMaxActive(dsProp.getMaxActive());
        }
        if (null != dsProp.getPassword()) {
            dataSource.setPassword(CipherUtil.decryptAesString(dataSource.getPassword()));
        }
        List<Filter> filters = new ArrayList<>();
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setDataSourceLogEnabled(false);
        slf4jLogFilter.setConnectionLogEnabled(false);
        slf4jLogFilter.setResultSetLogEnabled(false);
        slf4jLogFilter.setStatementParameterClearLogEnable(false);
        slf4jLogFilter.setStatementCreateAfterLogEnabled(false);
        slf4jLogFilter.setStatementCloseAfterLogEnabled(false);
        slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(false);
        filters.add(slf4jLogFilter);
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean(name = "myJdbcTemplate")
    public JdbcTemplate myJdbcTemplate(){
        return new JdbcTemplate(getMyDataSource());
    }

    /**
     * 告诉jpa我不仅声明了bean，还配给你了啊，别给我出幺蛾子
     */
    @Configuration
    @EnableJpaRepositories(basePackages = {"com.sh.ctrl.dao"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "platformTransactionManager")
    class Config{

    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(getMyDataSource());
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        //读取包的位置
        factoryBean.setPackagesToScan(dsProp.getEntityPackageToScan());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        if (StringUtils.isNotBlank(dsProp.getHibernateDialect())) {
            properties.put("hibernate.dialect", dsProp.getHibernateDialect());
        }
        properties.put("hibernate.show_sql", dsProp.getShowSql() == null ? false : dsProp.getShowSql());
        properties.put("hibernate.hbm2ddl.auto", dsProp.getHibernateHbm2ddlAuto());
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.cache.use_second_level_cache", "true");
        properties.put("hibernate.cache.use_query_cache", "true");
        properties.put("hibernate.transaction.flush_before_completion", "true");
        factoryBean.setJpaProperties(properties);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager platformTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }
}
