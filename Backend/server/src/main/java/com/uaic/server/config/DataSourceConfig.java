package com.uaic.server.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // TODO: look at EnvironmentConfig.java (and delete this)
    private void loadEnv() {
        try {

            Dotenv dotenv = Dotenv.configure().load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
            System.out.println("Read the .env variables");
        } catch (Exception e) {
            System.out.println("Coudn't read the .env file");
        }
    }

    @Bean
    public DataSource dataSource() {
        // Load env variables
        this.loadEnv();

        String environment = System.getProperty("STAGE");
        if (environment == null || environment.equalsIgnoreCase("test")) {
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
