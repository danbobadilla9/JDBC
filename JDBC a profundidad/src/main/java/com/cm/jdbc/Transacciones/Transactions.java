package com.cm.jdbc.Transacciones;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class Transactions {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        System.out.println("Connecting");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
        System.out.println("Connected");

        System.out.println("Executing script");
        RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
        System.out.println("Script executed");
        // apagamos el autocommit
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO person(name,last_name,nickname) VALUES(?,?,?)");
        // creando un escenario de rollback junto con el error
        Savepoint savepoint = null;
        try{
            ps.setString(1,"israel");
            ps.setString(2,"bobadilla");
            ps.setString(3,"thedevil0");
            ps.executeUpdate();
            savepoint = connection.setSavepoint("ok");
            ps.setString(1,null);
            ps.setString(2,"jose");
            ps.setString(3,"thedjos");
            ps.executeUpdate();
            ps.setString(1,"pepe");
            ps.setString(2,"hernandez");
            ps.setString(3,"thedevil0");
            ps.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            if(savepoint == null){
                // rollback de todo
                connection.rollback();
            }else{
                // rollback hasta el save point
                connection.rollback(savepoint);
            }
            System.out.println("Rolling back because "+e.getMessage());
        }finally {
            connection.setAutoCommit(true);
        }

        System.out.println("Records Persisted");

        PreparedStatement statement = connection.prepareStatement("SELECT*FROM person");
        ResultSet rs = statement.executeQuery();

        while(rs.next()){
            System.out.printf("id [%d] name [%s] lastame [%s] nickname[%s] \n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
        }

        ps.close();
        connection.close();

    }

}
