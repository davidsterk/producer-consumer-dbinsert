/*
 * Description: Class MySqlConnector. Creates a Mysql database connection
 */
package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnector {

  private static String connURL;
  private static String user;
  private static String userPassword;
  private static String driver;
  private MySqlConnector() {}

  /*
  Get database connection info from a properties file: connection.prop
   */
  static {
    try {
      FileInputStream fis = new FileInputStream("connection.prop");
      Properties prop = new Properties();
      prop.load(fis);
      connURL = (String) prop.get("connURL");
      user = (String) prop.get("user");
      userPassword = (String) prop.get("password");
      driver = (String) prop.get("driver");
      Class.forName(driver);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  /*
  Static method that returns a new Mysql connection
   */
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(connURL, user, userPassword);
    }


  }
