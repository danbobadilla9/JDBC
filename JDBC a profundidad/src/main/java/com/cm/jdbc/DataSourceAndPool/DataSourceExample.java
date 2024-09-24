package com.cm.jdbc.DataSourceAndPool;


import org.h2.jdbcx.JdbcDataSource;

// JDBC 2 agrego el concepto de DaataSource, representan una fuente de datos , es una forma más flexible de establecer conexiones en lugar de DriverManager
public class DataSourceExample
{


    public static void main(String[] args) {
        // JDBC utiliza DataSource, pero h2 nos da la implementacion utilizando JdbcDatasource
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:~/test");
        


    }
}
