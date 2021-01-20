package com.cm6123.snl.Database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtil {

  /**
   * A function that will establish connection to the server
   * @return - the connection object associated with the server
   */
  public static Connection connectToServer() {
    Connection con = null;
    Properties props = new Properties();
    String path = System.getProperty("user.dir");
    path += "/src/db.properties";
    FileInputStream propsFile = null;
    try{
      propsFile = new FileInputStream(path);
      props.load(propsFile);
      String url, username, password;
      url = props.getProperty("DB_URL");
      username = props.getProperty("DB_USER");
      password = props.getProperty("DB_PASS");
      con = DriverManager.getConnection(url, username, password);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return con;
  }

  /**
   * Get the database name, stored in the db.properties file
   * @return - name of the database
   */
  public static String getDBName() {
    Properties props = new Properties();
    String path = System.getProperty("user.dir");
    path += "/src/db.properties";
    FileInputStream propsFile = null;
    String db_name = "";
    try {
      propsFile = new FileInputStream(path);
      props.load(propsFile);
      db_name = props.getProperty("DB_NAME");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return db_name;
  }
}

