package com.liugh.config;

/**
 * 只提供了常用的属性，如果有需要，自己添加
 *
 * @author liuzh
 * @since 2017/2/5.
 */
//@Data
//@Component
//@ConfigurationProperties(prefix = DruidProperties.DRUID_PREFIX)
public class DruidProperties {
    public static final String DRUID_PREFIX = "spring.datasource";

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
    private boolean useGlobalDataSourceStat;

}
