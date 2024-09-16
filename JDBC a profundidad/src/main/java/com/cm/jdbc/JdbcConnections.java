package com.cm.jdbc;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

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
            ps.setString(1, "Israel");
            ps.setString(2, "bobadilla");
            ps.setString(3, "thedevil");
            int rows = ps.executeUpdate();

            System.out.println("Rows impacted: " + rows);

            ps.setString(1, "jose");
            ps.setString(2, "lopez");
            ps.setString(3, "lopitoz");
            rows = ps.executeUpdate();
            System.out.println("Rows impacted: " + rows);


            // uso de executeQuery
            PreparedStatement statementSelect = connection.prepareStatement("SELECT * FROM person");
            // uso de executeUpdate
            PreparedStatement statementDelete = connection.prepareStatement("DELETE FROM person");
            // uso de execute, este unicamente regresa un boolean, indicando lo siguiente, si es true el resultato es un resultset y podemos utilizar getResultSet o false si es un número de registros impactados, utilizando getUpdateCount obtenemos ese número
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person");


            // executequery siempre regresa un resultset , para poder iterarlo
            ResultSet rs = statementSelect.executeQuery();
            while (rs.next()) {
                System.out.printf("\n id = [%d] Name = [%s] \t Last name = [%s] \t Nickname= [%s] \n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }

            boolean flag = statement.execute();
            if (flag) {
                ResultSet rs2 = statement.getResultSet();
                while (rs2.next()) {
                    System.out.printf("\n id = [%d] Name = [%s] \t Last name = [%s] \t Nickname= [%s] \n", rs2.getInt(1), rs2.getString(2), rs2.getString(3), rs2.getString(4));
                }
            } else {
                int rowsImpacted = statement.getUpdateCount();
                System.out.println("Rows impacted: " + rowsImpacted);
            }
            // ExecuteUpdate se utiliza para operaciones DML, ya que regresa un número y si regresa 0 quiere decir que no
            // realizo ninguna operacion
            int rowsDeleted = statementDelete.executeUpdate();
            System.out.println("Rows deleted " + rowsDeleted);


            // diferencia entre statements y prepareStatemnt y calleablestatemnt
            // prepareStatement es una especialización de statement ya que lo hereda de la interfaz statement,
            // statemnts, permite ejecutar sentencias SQL pero no cmabiar los parametros de entrada en tiempo de ejecución, osea no admite los ?
            // prepareStatment, permite ejecutar multiples veces y agrega la habilidad de asignar los parametros los cuales se definen con el símbolo ?
            // callablestatement hereda de preparedstatement, permite ejecutar y obtener resultados de un procedimiento almacenado


            statementDelete.close();
            statementSelect.close();
            statement.close();
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
