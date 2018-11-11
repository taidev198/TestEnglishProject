package helper;

import java.sql.*;

public class ConnectDataHelper {
   private  static Connection conn = null;
   private  static String url = "jdbc:mysql://localhost/data";
   private  static String userName = "root";
   private  static String password = "03031998";
    private static ResultSet res;

    public  static Connection connectDB() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    public  void closeDB(){
        if (conn != null){
            try {
                conn.close();
                System.out.println("Disconnceted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  ResultSet getDB(){
        return res;
    }

    public Connection getConnection() {
        return conn;
    }
}
