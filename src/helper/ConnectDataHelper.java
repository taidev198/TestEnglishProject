package helper;

import java.sql.*;

public class ConnectDataHelper {
   private  static Connection conn = null;
   private static ConnectDataHelper instance= null;

    private ConnectDataHelper(){

   }

   public static ConnectDataHelper getInstance(){
       if (instance == null)
           instance = new ConnectDataHelper();
       return instance;
   }

    public  Connection connectDB() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        String password = "03031998";
        String userName = "taiadmin";
        String url = "jdbc:mysql://db4free.net:3306/testenglish";
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

}
