package com.sh.config.prop;

import lombok.Data;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/10 13:35
 */
@Data
public class BaseDataSourceProp {

    private String type;
    private String driverClassName;
    private String dbType;
    private String url;
    private String username;
    private String password;
    private String validationQuery;
    private Boolean testOnBorrow;
    private Boolean showSql;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private String privateKey;
    private String publicKey;
    private String[] entityPackageToScan;
    private String hibernateDialect;
    private String hibernateHbm2ddlAuto;
}
