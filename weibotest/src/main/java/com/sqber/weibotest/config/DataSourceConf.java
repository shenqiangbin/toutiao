package com.sqber.weibotest.config;

import com.sqber.weibotest.db.DBHelper;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConf {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DBHelper primaryDbHelper(DataSource dataSource) {
        return new DBHelper(dataSource);
    }

    // 多数据源：在有一个数据源时，配置如下
    @Bean("sqliteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlite") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean("sqliteDB")
    public DBHelper postgreSqlHelper(@Qualifier("sqliteDataSource") DataSource dataSource) {
        return new DBHelper(dataSource);
    }

//    /**
//     *     其他地方使用：
//     *     @Autowired
//     *     @Qualifier("sqliteDB")
//     *     private DBHelper postgreHelper;
//     */

}