package com.example.chattproject;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTests {

    @Test
    public void testConnection() throws Exception{

        Class.forName("org.mariadb.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb",
                "websuer",
                "1234");

        Assertions.assertNotNull(connection);

        connection.close();
    }

    @Test
    public void testHikariCP() throws Exception{

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("websuer");
        config.setPassword("1234");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        System.out.println(connection);

        connection.close();
    }


}
