package com.cm.jdbc.DataSourceAndPool;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Exercise {

    private static int NUM_CONNECTIONS = 100;
    public static void main(String[] args) throws SQLException {
        // vamos a crear varias conexiones
        long startTime = System.currentTimeMillis();
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/test","","");
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
//            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            Connection connection = connectionPool.getConnection();
            connection.close();
        }

        System.out.println("Total Time = "+(System.currentTimeMillis() - startTime)/1000+"s");
    }
























}
