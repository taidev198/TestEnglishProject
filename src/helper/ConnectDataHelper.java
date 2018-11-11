package helper;

import java.sql.*;

public class ConnectDataHelper {
   private Connection conn = null;
   private String url ;
   private String userName = "root";
   private String password = "03031998";
    private ResultSet res;

    public ConnectDataHelper(String url){
        this.url = url;
    }

    public  Connection connectDB() throws ClassNotFoundException, IllegalAccessException,
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
