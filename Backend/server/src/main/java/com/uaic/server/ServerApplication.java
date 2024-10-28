package com.uaic.server;

import com.uaic.server.entities.User;
import com.uaic.server.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.uaic.server.tests.InMemoryUserRepository;
import java.util.List;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ServerApplication {

	public static void main(String[] args) {
                
		SpringApplication.run(ServerApplication.class, args);
	}

}
