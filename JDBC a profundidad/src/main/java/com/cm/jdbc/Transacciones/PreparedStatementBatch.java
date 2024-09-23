package com.cm.jdbc.Transacciones;

import com.github.javafaker.Faker;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
public class PreparedStatementBatch {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        System.out.println("Connecting");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
        System.out.println("Connected");

        System.out.println("Executing script");
        RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
        System.out.println("Script executed");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO person(name,last_name,nickname) VALUES(?,?,?)");
        try{
            Faker faker = new Faker();
            connection.setAutoCommit(false);
            for (int i = 0; i < 100; i++) {
                ps.setString(1,faker.name().firstName());
                ps.setString(2,faker.name().lastName());
                ps.setString(3,faker.gameOfThrones().character());
                ps.addBatch();
            }
            // si un registro tiene un error, el resto si se va a insertar
            int[] rowsInserted = ps.executeBatch(); // numeros de registros impactados, en este caso solo seria solo uno
            for(int rowsImpacted: rowsInserted){
                System.out.println("Rowsimpacted: "+rowsImpacted);
            }
        }catch (SQLException e){
            System.out.println("Error: "+e.getMessage());
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
