package com.cm.jdbc;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnections {

    public static void main(String [] args){
        try {


            System.out.println("Connecting");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            System.out.println("Connected");

            System.out.println("Executing script");
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            System.out.println("Script executed");

            PreparedStatement ps = connection.prepareStatement("INSERT INTO person(name,last_name,nickname) values (?,?,?)");
            ps.setString(1,"Israel");
            ps.setString(2,"bobadilla");
            ps.setString(3,"thedevil");
            int rows = ps.executeUpdate();

            System.out.println("Rows impacted: "+rows);

            ps.setString(1,"jose");
            ps.setString(2,"lopez");
            ps.setString(3,"lopitoz");
            rows = ps.executeUpdate();
            System.out.println("Rows impacted: "+rows);

            ps.close();
            System.out.println("Closing");
            connection.close();
            System.out.println("Closed");



        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading the file");
            e.printStackTrace();
        }
    }


}
