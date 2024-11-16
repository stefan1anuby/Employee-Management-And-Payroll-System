package com.uaic.server.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@DependsOn("environmentConfig")
public class DataSourceConfig {


    @Bean
    public DataSource dataSource() {
        
        String environment = System.getProperty("STAGE");
        if (environment.equalsIgnoreCase("test")) {
            // Configure an in-memory H2 database for testing
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        } else {
            // Configure PostgreSQL database for other environments
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            String host = System.getProperty("POSTGRES_HOST");
            String port = System.getProperty("POSTGRES_PORT");
            String database = System.getProperty("POSTGRES_DB");
            String username = System.getProperty("POSTGRES_USER");
            String password = System.getProperty("POSTGRES_PASSWORD");
            dataSource.setUrl("jdbc:postgresql://" + host + ":" + port + "/" + database);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }
    }
}
