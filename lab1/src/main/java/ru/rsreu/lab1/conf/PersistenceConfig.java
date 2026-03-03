package ru.rsreu.lab1.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class PersistenceConfig {
    private final Environment environment;
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
//        return dataSource;
//    }

    @Bean
    public DataSource getDataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .type(org.postgresql.ds.PGSimpleDataSource.class)
                .url(environment.getRequiredProperty("jdbc.url"))
                .username(environment.getRequiredProperty("jdbc.username"))
                .password(environment.getRequiredProperty("jdbc.password"))
                .build();
        return dataSource;
    }
}
