package helper;

import java.sql.*;

public class ConnectDataHelper {
   private static Connection conn = null;
   private static String url = "jdbc:mysql://localhost/data";
   private static String userName = "root";
   private static String password = "03031998";

    public  static void connectDB() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement();
            statement.execute("use  data ");//if this returns, there is a result set, otherwise not.
            ResultSet res = statement.executeQuery("select * from user ");
            while (res.next()){
                String t = res.getString("username");//solution 2 using name of column: res.getString("username");
                String tmp = res.getString(2);
                //we can getObject() method to retrieve values as generic objects and convert the
                //values as necessary.
                Object o = res.getObject("password");
                System.out.println(t + " tmp:" + tmp);
            }
            System.out.println("connected");


    }

    public static void closeDB(){
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
