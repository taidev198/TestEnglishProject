package model;

import helper.ConnectDataHelper;
import javafx.scene.control.MenuButton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by traig on 7:29 AM, 12/1/2018
 */
public class QuestionModel {
    public QuestionModel(){}

    //getting data for user
    public Map<String, List<List<String>>> getTestGrammarFollowGrammarId(){
        String query = "select description,questionid, content, optionA, optionB, optionC, optionD, keyQuestion from question join grammar on grammar.grammarid = question.grammarid;";
        Map<String, List<List<String>>> res = new LinkedHashMap<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                List<String> idquestion = new ArrayList<>();
                List<String> content = new ArrayList<>();
                List<String> a = new ArrayList<>();
                List<String> b = new ArrayList<>();
                List<String> c = new ArrayList<>();
                List<String> d = new ArrayList<>();
                List<String> keyQuestion = new ArrayList<>();
                if (!res.containsKey(resultSet.getString("description"))){
                    List<List<String>> tmp = new ArrayList<>();
                    idquestion.add(resultSet.getString("idquestion"));
                    content.add(resultSet.getString("content"));
                    a.add(resultSet.getString("optionA"));
                    b.add(resultSet.getString("optionB"));
                    c.add(resultSet.getString("optionC"));
                    d.add(resultSet.getString("optionD"));
                    keyQuestion.add(resultSet.getString("keyQuestion"));
                    tmp.add(idquestion);
                    tmp.add(content);
                    tmp.add(a);
                    tmp.add(b);
                    tmp.add(c);
                    tmp.add(d);
                    tmp.add(keyQuestion);
                    res.put(resultSet.getString("description"), tmp);
                }else {
                    List<List<String>> tmp = new ArrayList<>(res.get(resultSet.getString("description")));
                    tmp.get(0).add(resultSet.getString("idquestion"));
                    tmp.get(1).add(resultSet.getString("content"));
                    tmp.get(2).add(resultSet.getString("optionA"));
                    tmp.get(3).add(resultSet.getString("optionB"));
                    tmp.get(4).add(resultSet.getString("optionC"));
                    tmp.get(5).add(resultSet.getString("optionD"));
                    tmp.get(6).add(resultSet.getString("keyQuestion"));
                    res.replace(resultSet.getString("description"), res.get(resultSet.getString("description")), tmp);
                }
            }

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;

    }
    //getting data for admin
    public  List<List<String>> getQuestion(){
        String query = "select * from question ;";
        List<List<String>> res = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);

            List<String> idquestion = new ArrayList<>();
            List<String> content = new ArrayList<>();
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            List<String> c = new ArrayList<>();
            List<String> d = new ArrayList<>();
            List<String> key = new ArrayList<>();
            List<String> grammarid = new ArrayList<>();
            List<String> contestid = new ArrayList<>();
            List<String> typeid = new ArrayList<>();
            while (resultSet.next()){
                idquestion.add(resultSet.getString("idquestion"));
                content.add(resultSet.getString("content"));
                a.add(resultSet.getString("optionA"));
                b.add(resultSet.getString("optionB"));
                c.add(resultSet.getString("optionC"));
                d.add(resultSet.getString("optionD"));
                key.add(resultSet.getString("keyQuestion"));
                grammarid.add(resultSet.getString("grammarid"));
                contestid.add(resultSet.getString("contestid"));
                typeid.add(resultSet.getString("typeid"));
            }
            res.add(idquestion);
            res.add(content);
            res.add(a);
            res.add(b);
            res.add(c);
            res.add(d);
            res.add(key);
            res.add(grammarid);
            res.add(contestid);
            res.add(typeid);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean removeQuestion(int id){
        String query = "delete from question where idquestion = " + id;
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean addNewQuestion(QuestionTableView question){

        String query = "insert into question(idquestion, content, optionA, optionB, optionC, optionD, keyQuestion, grammarid, contestid, typeid ) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.setInt(1, Integer.parseInt(question.getIdquestion()));
            statement.setString(2, question.getContent());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getKey());
            statement.setInt(8, Integer.parseInt(question.getGrammarid()));
            statement.setInt(9, Integer.parseInt(question.getContestid()));
            statement.setInt(10, Integer.parseInt(question.getTypeid()));
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void update(String id, Question question){

        String query = "update question SET idquestion = ?, content =  ?, optionA = ?, optionB = ?, optionC= ?, optionD = ?, keyQuestion = ?, grammarid = ?, contestid = ?, typeid =? WHERE idquestion = ?";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(question.getIdquestion()));
            statement.setString(2, question.getContent());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getKey());
            statement.setInt(8, Integer.parseInt(question.getGrammarid()));
            statement.setInt(9, Integer.parseInt(question.getContestid()));
            statement.setInt(10, Integer.parseInt(question.getTypeid()));
            statement.setInt(11, Integer.parseInt(id));
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static class Question{
        private String idquestion;
        private String content;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String key;
        private String grammarid;
        private String contestid;
        private String typeid;

        public Question(String idquestion,String content,  String optionA, String optionB,
                        String optionC, String optionD, String key,  String grammarid, String contestid, String typeid) {
            this.idquestion = idquestion;
            this.content = content;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.key = key;
            this.grammarid = grammarid;
            this.contestid = contestid;
            this.typeid = typeid;
        }

        public String getGrammarid() {
            return grammarid;
        }

        public void setGrammarid(String grammarid) {
            this.grammarid = grammarid;
        }

        public String getIdquestion() {
            return idquestion;
        }

        public void setIdquestion(String idquestion) {
            this.idquestion = idquestion;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }
    }

    public static class QuestionTableView extends Question{

        private MenuButton action;

        public QuestionTableView(String idquestion, String content, String optionA, String optionB, String optionC, String optionD, String key,
                                 String grammarid, String contestid, String typeid, MenuButton action) {
            super(idquestion, content, optionA, optionB, optionC, optionD, key, grammarid, contestid, typeid);
            this.action = action;
        }

        public MenuButton getAction() {
            return action;
        }

        public void setAction(MenuButton action) {
            this.action = action;
        }
    }


}
