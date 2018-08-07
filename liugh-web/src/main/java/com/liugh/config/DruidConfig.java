package com.liugh.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;


/**
 * Druid数据库连接池
 * @author : liugh
 * @date : 2018/05/08
 */

@Configuration
//@EnableConfigurationProperties(DruidProperties.class)
//@ConditionalOnClass(DruidDataSource.class)
public class DruidConfig {

    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServletBean() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams

        //白名单：
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny", "192.168.0.151");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "liugh");
        servletRegistrationBean.addInitParameter("loginPassword", "liugh");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * druid监控过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilterBean(){
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new WebStatFilter());
        filter.setName("druidWebStatFilter");
        filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filter.addUrlPatterns("/*");
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        return filter;
    }

}
