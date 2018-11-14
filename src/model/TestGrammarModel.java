package model;

import helper.ConnectDataHelper;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class TestGrammarModel {

    public TestGrammarModel(){}


    public Map<String, List<List<String>>> getTestGrammarFollowGrammarId(){
        String query = "select description,testgrammarid, questtion, a, b, c, d, testgrammar.key from testgrammar join grammar on grammar.grammarid = testgrammar.grammarid;";
        Map<String, List<List<String>>> res = new LinkedHashMap<>();
        try(Statement statement = ConnectDataHelper.connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);

            List<String> testgrammarid = new ArrayList<>();
            List<String> question = new ArrayList<>();
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            List<String> c = new ArrayList<>();
            List<String> d = new ArrayList<>();
            List<String> key = new ArrayList<>();
            while (resultSet.next()){

                if (!res.containsKey(resultSet.getString("description"))){
                    List<List<String>> tmp = new ArrayList<>();
                    testgrammarid.add(resultSet.getString("testgrammarid"));
                    question.add(resultSet.getString("questtion"));
                    a.add(resultSet.getString("a"));
                    b.add(resultSet.getString("b"));
                    c.add(resultSet.getString("c"));
                    d.add(resultSet.getString("d"));
                    key.add(resultSet.getString("key"));
                    tmp.add(testgrammarid);
                    tmp.add(question);
                    tmp.add(a);
                    tmp.add(b);
                    tmp.add(c);
                    tmp.add(d);
                    tmp.add(key);
                    res.put(resultSet.getString("description"), tmp);
                }else {

                    List<List<String>> tmp = new ArrayList<>(res.get(resultSet.getString("description")));
                    tmp.get(0).add(resultSet.getString("testgrammarid"));
                    tmp.get(1).add(resultSet.getString("questtion"));
                    tmp.get(2).add(resultSet.getString("a"));
                    tmp.get(3).add(resultSet.getString("b"));
                    tmp.get(4).add(resultSet.getString("c"));
                    tmp.get(5).add(resultSet.getString("d"));
                    tmp.get(6).add(resultSet.getString("key"));
                    res.replace(resultSet.getString("description"), res.get(resultSet.getString("description")), tmp);
                }
            }

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return res;

    }

    public  List<List<String>> getTestGrammar(){
        String query = "select * from testgrammar ;";
        List<List<String>> res = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);

            List<String> testgrammarid = new ArrayList<>();
            List<String> question = new ArrayList<>();
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            List<String> c = new ArrayList<>();
            List<String> d = new ArrayList<>();
            List<String> key = new ArrayList<>();
            List<String> grammarid = new ArrayList<>();
            while (resultSet.next()){
                    testgrammarid.add(resultSet.getString("testgrammarid"));
                    question.add(resultSet.getString("questtion"));
                    a.add(resultSet.getString("a"));
                    b.add(resultSet.getString("b"));
                    c.add(resultSet.getString("c"));
                    d.add(resultSet.getString("d"));
                    key.add(resultSet.getString("key"));
                    grammarid.add(resultSet.getString("grammarid"));

            }
            res.add(testgrammarid);
            res.add(question);
            res.add(a);
            res.add(b);
            res.add(c);
            res.add(d);
            res.add(key);
            res.add(grammarid);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return res;

    }

    public boolean removeTestGrammar(int id){
        String query = "alter from testgrammar where id = " + id;
        try(PreparedStatement statement = ConnectDataHelper.connectDB().prepareStatement(query)) {
            statement.execute("use data");
          statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addNewTestGrammar(QuestionTableView question){

        String query = "insert into testgrammar(testgrammarid, questtion, a, b, c, d,testgrammar.key, grammarid) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.setInt(1, Integer.parseInt(question.getNumber()));
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getKey());
            statement.setInt(8, Integer.parseInt(question.getGrammarid()));
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static class Question{
        private String content;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String ans;
        private String key;
        private String number;
        private String grammarid;

        public Question(String content, String question, String optionA, String optionB,
                        String optionC, String optionD, String ans, String key, String number, String grammarid) {
            this.content = content;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.ans = ans;
            this.key = key;
            this.number = number;
            this.grammarid = grammarid;
        }

        public String getGrammarid() {
            return grammarid;
        }

        public void setGrammarid(String grammarid) {
            this.grammarid = grammarid;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getAns() {
            return ans;
        }

        public void setAns(String ans) {
            this.ans = ans;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class QuestionTableView extends Question{

        private MenuButton action;

        public QuestionTableView(String content, String question, String optionA, String optionB, String optionC, String optionD, String ans, String key,
                                 String number, String grammarid, MenuButton menuButton) {
            super(content, question, optionA, optionB, optionC, optionD, ans, key, number, grammarid);
//            MenuItem edit = new MenuItem("edit");
//            MenuItem delete = new MenuItem("delete");

//            action.setText("...");
//            action.getItems().addAll(edit);
//            action.getItems().addAll(delete);
            this.action = menuButton;
        }

        public MenuButton getAction() {
            return action;
        }

        public void setAction(MenuButton action) {
            this.action = action;
        }
    }

}
