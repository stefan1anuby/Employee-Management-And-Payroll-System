package com.uaic.server;

import com.uaic.server.config.DataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
@ContextConfiguration(classes = DataSourceConfig.class)
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connection successful: " + !connection.isClosed());
        }
    }
}
