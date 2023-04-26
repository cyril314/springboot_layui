package com.fit.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.fit.interceptor.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * druid数据库连接池的配置类(必须与启动类包目录同级)
 */
@Configuration
@MapperScan("com.fit.dao")
public class DruidConfig {

    @Value("${mybatis.mapper-locations}")
    private Resource[] MAPPER_LOCATIONS;
    @Value("${mybatis.type-aliases-package}")
    private String ALIASES_PACKAGE;

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        // 创建servlet注册实体
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
        // 设置ip白名单
        //servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 设置ip黑名单，如果allow与deny共同存在时,deny优先于allow
        //servletRegistrationBean.addInitParameter("deny", "192.168.0.1");
        // 设置控制台管理用户
        //servletRegistrationBean.addInitParameter("loginUsername", "druid");
        //servletRegistrationBean.addInitParameter("loginPassword", "fhadmin");
        // 是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        // 创建过滤器
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
        // 设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }


    @Bean
    public Interceptor pageInterceptor() {
        PageInterceptor pagePlugin = new PageInterceptor();
        Properties p = new Properties();
        p.put("dialect", "mysql");
        p.put("pageSqlId", ".*listPage.*");
        pagePlugin.setProperties(p);
        return pagePlugin;
    }

    /**
     * session工厂
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor()});
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setTypeAliasesPackage(ALIASES_PACKAGE);
        factoryBean.setMapperLocations(MAPPER_LOCATIONS);
        return factoryBean.getObject();
    }

    /**
     * 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
