package com.sh.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/10 13:30
 */
@ConfigurationProperties(prefix = "com.sh.datasource")
public class DsProp extends BaseDataSourceProp{

}
