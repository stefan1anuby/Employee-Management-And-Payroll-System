package com.uaic.server.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    //TODO: look at EnvironmentConfig.java (and delete this)
    private void loadEnv(){
        Dotenv dotenv = Dotenv.configure().directory("../../").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Bean
    public DataSource dataSource() {

        // load env variables
        this.loadEnv();

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
