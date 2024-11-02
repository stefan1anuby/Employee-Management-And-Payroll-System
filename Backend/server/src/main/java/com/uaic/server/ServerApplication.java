package com.uaic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EntityScan("com.uaic.server.entities")
@EnableJpaRepositories("com.uaic.server.repositories") 
@EnableWebSecurity
public class ServerApplication {

	public static void main(String[] args) {
                
		SpringApplication.run(ServerApplication.class, args);
	}

}
