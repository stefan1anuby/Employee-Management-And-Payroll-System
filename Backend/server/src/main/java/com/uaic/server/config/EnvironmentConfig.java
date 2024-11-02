package com.uaic.server.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@Configuration
public class EnvironmentConfig {

    @PostConstruct
    public void loadEnvVariables() {

        // WHY THE FUCK THIS DOESN'T GET CALLED
        // TODO: GET IT WORKING

        System.out.println("Trying to load environment variables.");
        // Load the .env file located in the specified directory
        Dotenv dotenv = Dotenv.configure().directory("../../").load();

        // Set each .env entry as a system property
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        System.out.println("Environment variables loaded successfully.");
    }
}
