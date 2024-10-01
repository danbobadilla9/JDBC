package com.cm.jdbc.DataSourceAndPool;


import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

// JDBC 2 agrego el concepto de DaataSource, representan una fuente de datos , es una forma más flexible de establecer conexiones en lugar de DriverManager
public class DataSourceExample
{


    public static void main(String[] args) throws FileNotFoundException {
        // JDBC utiliza DataSource, pero h2 nos da la implementacion utilizando JdbcDatasource
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:~/test");
        Connection connection = null;
        try {

            connection = jdbcDataSource.getConnection();
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }
}
