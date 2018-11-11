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
//            Statement statement = conn.createStatement();
//            statement.execute("use  data ");//if this returns, there is a result set, otherwise not.
//            res = statement.executeQuery("select * from user ");
//            while (res.next()){
//                String t = res.getString("username");//solution 2 using name of column: res.getString("username");
//                String tmp = res.getString(2);
//                //we can getObject() method to retrieve values as generic objects and convert the
//                //values as necessary.
//                Object o = res.getObject("password");
//                System.out.println(t + " tmp:" + tmp);
//            }

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
