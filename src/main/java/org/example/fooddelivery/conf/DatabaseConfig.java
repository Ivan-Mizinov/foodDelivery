package org.example.fooddelivery.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    public String dbUrl;

    @Value("${spring.datasource.username}")
    public String dbUsername;

    @Value("${spring.datasource.password}")
    public String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    public String dbDriverClassName;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .driverClassName(dbDriverClassName)
                .type(DriverManagerDataSource.class)
                .build();
    }
}
