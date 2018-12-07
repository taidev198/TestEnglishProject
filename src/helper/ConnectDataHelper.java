package helper;

import java.sql.*;

public class ConnectDataHelper {
   private  static Connection conn = null;
   private static ConnectDataHelper instance= null;

    private ConnectDataHelper() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String password = "iVqm7FVt6R";
//        String userName = "taiadmin";
//        String url = "jdbc:mysql://db4free.net:3306/testenglish";
//        String userName = "root";
//        String url = "jdbc:mysql://localhost:3306/data";
        String userName = "sql12268841";
        String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12268841";
        conn = DriverManager.getConnection(url, userName, password);
   }

   public static ConnectDataHelper getInstance() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
       if (instance == null)
           instance = new ConnectDataHelper();
       return instance;
   }


    public  Connection connectDB() {
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
