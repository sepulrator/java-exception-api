package org.javaex.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.javaex.properties.util.ExceptionConfigUtil;


public class DBConnectionManager {
  
  private static String url = ExceptionConfigUtil.getProperty("data.source.URL");
  private static String driverName = ExceptionConfigUtil.getProperty("data.source.driver");   
  private static String username = ExceptionConfigUtil.getProperty("data.source.username");   
  private static String password = ExceptionConfigUtil.getProperty("data.source.password");
  private static Connection connection;
  
  public static Connection getConnection() {
    try {
        Class.forName(driverName);
        try {
          connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.err.println("Failed to create the database connection."); 
        }
    } catch (ClassNotFoundException ex) {
        // log an exception. for example:
        System.err.println("Driver not found."); 
    }
    return connection;
}

}
