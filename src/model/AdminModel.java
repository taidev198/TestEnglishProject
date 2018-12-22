package model;

import helper.ConnectDataHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by traig on 7:20 PM, 12/22/2018
 */
public class AdminModel  {

   public  Map<String, int[]> getResultOfContests(){
       Map<String, int[]> ans = new HashMap<>();
       List<String> description = new ArrayList<>();
       String query = "select description,  count(temp.numOfCorrect) as 'totalHighScore' from (select  userinfo.userInfoid, resultid, numOfCorrect from userinfo join testresult on userinfo.userInfoid = testresult.userInfoid and testresult.typeresultid =1 and numOfCorrect >1)  as temp join quiztest\n" +
               "on temp.resultid = quiztest.idquiztest group by description;";
       try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
           statement.execute("use data");
           ResultSet resultSet = statement.executeQuery(query);
           while (resultSet.next()) {
               int[] tmp = new int[3];
               tmp[0] = resultSet.getInt("totalHighScore");
               String temp = resultSet.getString("description");
                ans.put(temp, tmp);
                description.add(temp);
           }

           query = "select description,  count(temp.numOfCorrect) as 'totalAverScore' from (select  userinfo.userInfoid, resultid, numOfCorrect from userinfo join testresult on userinfo.userInfoid = testresult.userInfoid and testresult.typeresultid =1 and numOfCorrect =0)  as temp join quiztest\n" +
                   "on temp.resultid = quiztest.idquiztest group by description;";
            resultSet = statement.executeQuery(query);
           while (resultSet.next()) {
               String temp = resultSet.getString("description");
               if (ans.containsKey(temp)){
                   int[] tmp = ans.get(temp);
                   tmp[1] = resultSet.getInt("totalAverScore");
                   ans.replace(temp, ans.get(temp), tmp);

               }else {
                   int[] tmp = new int[3];
                   tmp[1] = resultSet.getInt("totalAverScore");
                   ans.put(temp, tmp);
                   description.add(temp);
               }

           }

       } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }


       return ans;


   }
}
