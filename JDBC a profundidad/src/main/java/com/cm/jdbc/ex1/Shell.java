package com.cm.jdbc.ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Shell {

    public static void main(String[] args) throws SQLException, IOException {


            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
        String command = readCommand();

        while(!"quit".equals(command)){
            try{
                Statement  statement=  connection.createStatement();
                boolean resultType = statement.execute(command);
                if(resultType){
                    // resultset
                    ResultSet rs = statement.getResultSet();
                    while(rs.next()){
                        ResultSetMetaData metaData = rs.getMetaData();
                        for (int i = 1; i < metaData.getColumnCount(); i++){
                            String value = rs.getString(i);
                            System.out.print("\t "+value);
                        }
                        System.out.println();
                    }
                }else{
                    // update
                    System.out.println("Rows impacted: "+statement.getUpdateCount());
                }
            }catch (SQLException e){
                System.out.printf("Error %s executing statement %s ",e.getMessage(), command);
            }finally {
                command = readCommand();
            }

        }

            connection.close();
    }


    public static String readCommand() throws IOException {
        System.out.printf("\n dibs->");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }


}
