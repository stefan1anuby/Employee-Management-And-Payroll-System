package com.uaic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableWebSecurity
public class ServerApplication {

	public static void main(String[] args) {
                
		SpringApplication.run(ServerApplication.class, args);
	}

}
