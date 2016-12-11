package org.javaex.exception.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.javaex.database.util.DBConnectionManager;
import org.javaex.properties.util.ExceptionConfigUtil;

public class DatabaseDefinitionServiceImpl implements DatabaseDefinitionService {
  
  private Connection connection;
  private static final String TABLE_NAME = ExceptionConfigUtil.getProperty("data.source.tableName");
  
  public String getErrorMessage(String errorKey) {
    connection = DBConnectionManager.getConnection();
    tableDefinitionProcess();
    return getErrorMessageFromTable(errorKey);
  }

  private void tableDefinitionProcess() {
    boolean tableExists = isTableExists();
    System.out.println("tableExists:" + tableExists);
    if (tableExists) {
      return;
    }
    try {
      createTable();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isTableExists() {
    try {
      DatabaseMetaData dbm = connection.getMetaData();
      
      ResultSet tables = dbm.getTables(connection.getCatalog(), connection.getSchema(), TABLE_NAME.toLowerCase(), new String[] {"TABLE"});
      return tables.next();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
  
  private void createTable() throws Exception {
    StringBuilder sqlCreate = new StringBuilder();
    
    sqlCreate.append("CREATE TABLE " + TABLE_NAME)
             .append(" (ERROR_CODE    VARCHAR(10) PRIMARY KEY,"
                      + "MESSAGE    VARCHAR(20))");

    Statement stmt = connection.createStatement();
    stmt.executeUpdate(sqlCreate.toString());
    stmt.close();
    
  }
  
  private String getErrorMessageFromTable(String errorCode) {
    StringBuilder sqlCreate = new StringBuilder();
    String query="select * from "+TABLE_NAME + " where error_code=?";
    PreparedStatement p;
    try {
      p = connection.prepareStatement(query);
      p.setString(1, errorCode);
      ResultSet rs=p.executeQuery();
      while (rs.next()) {
        return rs.getString("MESSAGE");
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getErrorMessage(String... errorKeyParameters) {
    // TODO Auto-generated method stub
    return null;
  }
}
