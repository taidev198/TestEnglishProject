package model;

import helper.ConnectDataHelper;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class GrammarModel  {

    public GrammarModel() {

    }

    public void addGrammar(Grammar grammar){
        String query = "insert grammar(description, content) values(?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, grammar.des);
            statement.setString(2, grammar.content);
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void update(Grammar grammar, int id){
        String query = "update grammar SET grammarid = ?, description =  ?, content = ?  WHERE grammarid = ?";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(grammar.getId()));
            statement.setString(2, grammar.getDes());
            statement.setString(3, grammar.getContent());
            statement.setInt(4, id);
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrammar(int id){
        String query = "delete from grammar where id = ?";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public List<List<String>> getGrammar(){
        String query = "select * from grammar";
        List<List<String>> ans = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {

            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            List<String> id = new ArrayList<>();
            List<String> des = new ArrayList<>();
            List<String> content = new ArrayList<>();
            while (resultSet.next()){
                id.add(String.valueOf((resultSet.getInt("grammarid"))));
                des.add(resultSet.getString("description"));
                content.add(resultSet.getString("content"));
            }
            ans.add(id);
            ans.add(des);
            ans.add(content);
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return ans;

    }


    public boolean addResult(GrammarResult testResult){
        java.util.Date date = new Date();
        String query = "insert into testresult(userInfoid, typeresultid, resultid, numOfCorrect, numOfInCorrect,times, date) values( ?,?, ?, ?, ?, ?, ? )";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.setInt(1, Integer.parseInt(testResult.getUserInfoid()));
            statement.setInt(2, Integer.parseInt(testResult.getTyperesultid()));
            statement.setInt(3, Integer.parseInt(testResult.getResultid()));
            statement.setInt(4, Integer.parseInt(testResult.getNumOfCorrect()));
            statement.setString(5, testResult.getNumOfIncorrect());
            statement.setInt(6, Integer.parseInt(testResult.getTimes()));
            statement.setTimestamp(7, new Timestamp(date.getTime()));
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<List<String>> getResultGramarByAdmin(int userId){
        List<List<String>> res = new ArrayList<>();
        String query = "select  description, numOfCorrect, numOfIncorrect, times, totalTime, date from testresult join grammar\n" +
                "                 on testresult.resultid = grammar.grammarid and typeresultid = 0 and userInfoid = 1 order by description;";
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            List<String> description = new ArrayList<>();
            List<String> numOfCorrect = new ArrayList<>();
            List<String> numOfIncorrect = new ArrayList<>();
            List<String> times = new ArrayList<>();
            List<String> totalTime = new ArrayList<>();
            List<String> date = new ArrayList<>();
            while (resultSet.next()){
                description.add(resultSet.getString("description"));
                numOfCorrect.add(String.valueOf(resultSet.getInt("numOfCorrect")));
                numOfIncorrect.add(String.valueOf(resultSet.getInt("numOfIncorrect")));
                times.add(String.valueOf(resultSet.getInt("times")));
                totalTime.add(resultSet.getString("totalTime"));
                date.add(String.valueOf(resultSet.getTimestamp("date")));
            }
            if (description.size() > 0){
                res.add(description);
                res.add(numOfCorrect);
                res.add(numOfIncorrect);
                res.add(times);
                res.add(totalTime);
                res.add(date);
            }

        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public Map<String, List<List<String>>> getTestGrammar(){
        String query ="select description,grammar.grammarid, question.content, optionA, optionB, optionC, optionD, keyQuestion from grammar join question on grammar.grammarid = question.grammarid;";
        Map<String, List<List<String>>> listTestGrammar = new HashMap<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                List<String> testgrammarid = new ArrayList<>();
                List<String> question = new ArrayList<>();
                List<String> a = new ArrayList<>();
                List<String> b = new ArrayList<>();
                List<String> c = new ArrayList<>();
                List<String> d = new ArrayList<>();
                List<String> key = new ArrayList<>();
                if (!listTestGrammar.containsKey(resultSet.getString("description"))){
                    List<List<String>> tmp = new ArrayList<>();
                    testgrammarid.add(resultSet.getString("grammar.grammarid"));
                    question.add(resultSet.getString("question.content"));
                    a.add(resultSet.getString("optionA"));
                    b.add(resultSet.getString("optionB"));
                    c.add(resultSet.getString("optionC"));
                    d.add(resultSet.getString("optionD"));
                    key.add(resultSet.getString("keyQuestion"));
                    tmp.add(testgrammarid);
                    tmp.add(question);
                    tmp.add(a);
                    tmp.add(b);
                    tmp.add(c);
                    tmp.add(d);
                    tmp.add(key);
                    listTestGrammar.put(resultSet.getString("description"), tmp);
                }else {
                    List<List<String>> tmp = new ArrayList<>(listTestGrammar.get(resultSet.getString("description")));
                    tmp.get(0).add(resultSet.getString("grammar.grammarid"));
                    tmp.get(1).add(resultSet.getString("question.content"));
                    tmp.get(2).add(resultSet.getString("optionA"));
                    tmp.get(3).add(resultSet.getString("optionB"));
                    tmp.get(4).add(resultSet.getString("optionC"));
                    tmp.get(5).add(resultSet.getString("optionD"));
                    tmp.get(6).add(resultSet.getString("keyQuestion"));
                    listTestGrammar.replace(resultSet.getString("description"), listTestGrammar.get(resultSet.getString("description")), tmp);
                }
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listTestGrammar;
    }

    //getting totalGrammar
    public int getTotalGrammar() {
        String query = "select count(distinct grammarid) as 'total' from grammar";
        try (Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
              return   resultSet.getInt("total");
            }

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //getting getTotalCompletedGrammarByUser
    /**@param id Integer*/
    public int getTotalCompletedGrammarByUser(int id) {
        String query = "select count(distinct resultid) as 'totalCompletedGrammar' from testresult where typeresultid = 0 and userInfoid =" + id;
        try (Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt("totalCompletedGrammar");
            }
        }
             catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTotalCompletedGrammarByAdmin() {
        String query = "select count(distinct resultid) as 'totalCompletedGrammar' from testresult where typeresultid = 0 " ;
        try (Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt("totalCompletedGrammar");
            }
        }
        catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }



        public static class Grammar {
        private String id;
        private String des;
        private String content;
        private MenuButton menuButton;

        public Grammar(String id, String des, String content, MenuButton menuButton) {
            this.id = id;
            this.des = des;
            this.content = content;
            this.menuButton = menuButton;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MenuButton getMenuButton() {
            return menuButton;
        }

        public void setMenuButton(MenuButton menuButton) {
            this.menuButton = menuButton;
        }
    }

    public static class GrammarResult {
        private String userInfoid;
        private String typeresultid;
        private String resultid;
        private String numOfCorrect;
        private String numOfIncorrect;
        private String times;

        public GrammarResult(String userInfoid, String typeresultid, String resultid, String numOfCorrect, String numOfIncorrect, String times) {
            this.userInfoid = userInfoid;
            this.typeresultid = typeresultid;
            this.resultid = resultid;
            this.numOfCorrect = numOfCorrect;
            this.numOfIncorrect = numOfIncorrect;
            this.times = times;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getUserInfoid() {
            return userInfoid;
        }

        public void setUserInfoid(String userInfoid) {
            this.userInfoid = userInfoid;
        }

        public String getTyperesultid() {
            return typeresultid;
        }

        public void setTyperesultid(String typeresultid) {
            this.typeresultid = typeresultid;
        }

        public String getResultid() {
            return resultid;
        }

        public void setResultid(String resultid) {
            this.resultid = resultid;
        }

        public String getNumOfCorrect() {
            return numOfCorrect;
        }

        public void setNumOfCorrect(String numOfCorrect) {
            this.numOfCorrect = numOfCorrect;
        }

        public String getNumOfIncorrect() {
            return numOfIncorrect;
        }

        public void setNumOfIncorrect(String numOfIncorrect) {
            this.numOfIncorrect = numOfIncorrect;
        }
    }
}
