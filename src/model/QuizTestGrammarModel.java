package model;

import helper.ConnectDataHelper;
import javafx.scene.control.MenuButton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by traig on 4:35 PM, 11/12/2018
 */
public class QuizTestGrammarModel {

    public QuizTestGrammarModel(){}

    public List<List<String>> getDB(){
        String query = " select * from quizzcontest";
        List<List<String>> ans = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            List<String> quizzcontestid = new ArrayList<>();
            List<String> question = new ArrayList<>();
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            List<String> c = new ArrayList<>();
            List<String> d = new ArrayList<>();
            List<String> key = new ArrayList<>();
            List<String> contestid = new ArrayList<>();
            List<String> grammarid = new ArrayList<>();
            while (resultSet.next()){
                quizzcontestid.add(resultSet.getString("quizzcontestid"));
                question.add(resultSet.getString("question"));
                a.add(resultSet.getString("a"));
                b.add(resultSet.getString("b"));
                c.add(resultSet.getString("c"));
                d.add(resultSet.getString("d"));
                key.add(resultSet.getString("key"));
                contestid.add(String.valueOf(resultSet.getInt("contestid")));
                grammarid.add(String.valueOf(resultSet.getInt("grammarid")));
            }
            ans.add(quizzcontestid);
            ans.add(question);
            ans.add(a);
            ans.add(b);
            ans.add(c);
            ans.add(d);
            ans.add(key);
            ans.add(contestid);
            ans.add(grammarid);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public boolean removeQuizzTestGrammar(int id){
        String query = "delete from quizzcontest where quizzcontestid = " + id;
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addNewTestGrammar(QuizzQuesstion question){

        String query = "insert into quizzcontest(quizzcontestid, question, a, b, c, d,quizzcontest.key,contestid, grammarid) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.setInt(1, Integer.parseInt(question.getQuizzcontestid()));
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getKey());
            statement.setInt(8, Integer.parseInt(question.getContestid()));
            statement.setInt(9, Integer.parseInt(question.getGrammarid()));
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static class QuizzQuesstion{
        private String quizzcontestid;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String key;
        private String contestid;
        private String grammarid;

        public QuizzQuesstion(String quizzcontestid, String question, String optionA, String optionB, String optionC,
                              String optionD, String key, String contestid, String grammarid) {
            this.quizzcontestid = quizzcontestid;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.key = key;
            this.contestid = contestid;
            this.grammarid = grammarid;
        }

        public String getQuizzcontestid() {
            return quizzcontestid;
        }

        public void setQuizzcontestid(String quizzcontestid) {
            this.quizzcontestid = quizzcontestid;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getContestid() {
            return contestid;
        }

        public void setContestid(String contestid) {
            this.contestid = contestid;
        }

        public String getGrammarid() {
            return grammarid;
        }

        public void setGrammarid(String grammarid) {
            this.grammarid = grammarid;
        }
    }

    public static class QuizzQuesstionView extends QuizzQuesstion{
        private MenuButton menuButton;
        public QuizzQuesstionView(String quizzcontestid, String question, String optionA, String optionB,
                                  String optionC, String optionD, String key, String contestid, String grammarid, MenuButton menuButton) {
            super(quizzcontestid, question, optionA, optionB, optionC, optionD, key, contestid, grammarid);
            this.menuButton = menuButton;
        }

        public MenuButton getMenuButton() {
            return menuButton;
        }

        public void setMenuButton(MenuButton menuButton) {
            this.menuButton = menuButton;
        }
    }
}
