package model;

import helper.ConnectDataHelper;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by traig on 4:35 PM, 11/12/2018
 */

/**https://stackoverflow.com/questions/21119096/how-to-insert-timestamp-in-database*/
public class QuizTestModel {

    public QuizTestModel(){}


    public void update(String id, QuizzQuesstion quizzQuesstion ){
        String query = "update quizzcontest SET quizzcontestid = ?, questtion =  ?, a = ?, b = ?, c= ?, d = ?, quizzcontest.key = ?, contestid = ? WHERE quizzcontestid = ?";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(quizzQuesstion.getQuizzcontestid()));
            statement.setString(2, quizzQuesstion.getQuestion());
            statement.setString(3, quizzQuesstion.getOptionA());
            statement.setString(4, quizzQuesstion.getOptionB());
            statement.setString(5, quizzQuesstion.getOptionC());
            statement.setString(6, quizzQuesstion.getOptionD());
            statement.setString(7, quizzQuesstion.getKey());
            statement.setInt(8, Integer.parseInt(quizzQuesstion.getContestid()));
            statement.setInt(9, Integer.parseInt(id));
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addResult(TestResult testResult){
        java.util.Date date = new Date();
        String query = "insert into testresult values( ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.setInt(1, Integer.parseInt(testResult.getUserInfoid()));
            statement.setInt(2, Integer.parseInt(testResult.getTyperesultid()));
            statement.setInt(3, Integer.parseInt(testResult.getResultid()));
            statement.setInt(4, Integer.parseInt(testResult.getNumOfCorrect()));
            statement.setInt(5, Integer.parseInt(testResult.getNumOfIncorrect()));
            statement.setInt(6, Integer.parseInt(testResult.getTimes()));
            statement.setString(7, testResult.getTotalTime());
            statement.setTimestamp(8, new Timestamp(date.getTime()));
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<List<String>> getResult(int id){
        String query = "select * from testresult where userInfoid = "+id +" and typeresultid = 1 order by  resultid";
        List<List<String>> ans = new ArrayList<>();
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);

            List<String> userInfoid = new ArrayList<>();
            List<String> resultid = new ArrayList<>();
            List<String> numOfCorrect = new ArrayList<>();
            List<String> numOfIncorrect = new ArrayList<>();
            List<String> times = new ArrayList<>();
            List<String> totalTime = new ArrayList<>();
            List<String> date = new ArrayList<>();
            while (resultSet.next()){
                userInfoid.add(String.valueOf(resultSet.getInt("userInfoid")));
                resultid.add(String.valueOf(resultSet.getInt("resultid")));
                numOfCorrect.add(String.valueOf(resultSet.getInt("numOfCorrect")));
                numOfIncorrect.add(String.valueOf(resultSet.getInt("numOfIncorrect")));
                times.add(String.valueOf(resultSet.getInt("times")));
                totalTime.add(String.valueOf(resultSet.getInt("totalTime")));
                date.add(String.valueOf(resultSet.getTimestamp("date")));
            }
            ans.add(userInfoid);
            ans.add(resultid);
            ans.add(numOfCorrect);
            ans.add(numOfIncorrect);
            ans.add(times);
            ans.add(totalTime);
            ans.add(date);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**@param id Integer*/
    public int getTotalCompletedContestByUser(int id) {
        String query = "select count(distinct resultid) as 'totalCompletedContest' from testresult where typeresultid = 1 and userInfoid =" + id;
        try (Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt("totalCompletedContest");
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTotalCompletedContestByAdmin() {
        String query = "select count(distinct resultid) as 'totalCompletedContest' from testresult where typeresultid = 1 ";
        try (Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt("totalCompletedContest");
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTotalContest() {
        String query = "select count(distinct idquiztest) as 'total' from quiztest";
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

    public TreeMap<String, List<List<String>>> getContest(){
        String query ="select description,idquiztest, content, optionA, optionB, optionC, optionD, keyQuestion from quiztest join question on quiztest.idquiztest = question.contestid " +"order by question.contestid;" ;
        TreeMap<String, List<List<String>>> listContests = new TreeMap<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                List<String> quiztestid = new ArrayList<>();
                List<String> question = new ArrayList<>();
                List<String> a = new ArrayList<>();
                List<String> b = new ArrayList<>();
                List<String> c = new ArrayList<>();
                List<String> d = new ArrayList<>();
                List<String> key = new ArrayList<>();
                if (!listContests.containsKey(resultSet.getString("description"))){
                    List<List<String>> tmp = new ArrayList<>();
                    quiztestid.add(resultSet.getString("idquiztest"));
                    question.add(resultSet.getString("content"));
                    a.add(resultSet.getString("optionA"));
                    b.add(resultSet.getString("optionB"));
                    c.add(resultSet.getString("optionC"));
                    d.add(resultSet.getString("optionD"));
                    key.add(resultSet.getString("keyQuestion"));
                    tmp.add(quiztestid);
                    tmp.add(question);
                    tmp.add(a);
                    tmp.add(b);
                    tmp.add(c);
                    tmp.add(d);
                    tmp.add(key);
                    listContests.put(resultSet.getString("description"), tmp);
                }else {
                    List<List<String>> tmp = new ArrayList<>(listContests.get(resultSet.getString("description")));
                    tmp.get(0).add(resultSet.getString("idquiztest"));
                    tmp.get(1).add(resultSet.getString("content"));
                    tmp.get(2).add(resultSet.getString("optionA"));
                    tmp.get(3).add(resultSet.getString("optionB"));
                    tmp.get(4).add(resultSet.getString("optionC"));
                    tmp.get(5).add(resultSet.getString("optionD"));
                    tmp.get(6).add(resultSet.getString("keyQuestion"));
                    listContests.replace(resultSet.getString("description"), listContests.get(resultSet.getString("description")), tmp);
                }


            }System.out.println(listContests);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listContests;
    }


    public List<List<String>> getResultByAdmin(int userId){
        List<List<String>> res = new ArrayList<>();
        String query = "select  description, numOfCorrect, numOfIncorrect, times, totalTime, date from testresult join quiztest\n" +
                " on testresult.resultid = quiztest.idquiztest and typeresultid = 1 and userInfoid = "+ userId + " order by description;";
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

        String query = "insert into quizzcontest(quizzcontestid, question, a, b, c, d,quizzcontest.key,contestid) values(?, ?, ?, ?, ?, ?, ?, ?)";
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


        public QuizzQuesstion(String quizzcontestid, String question, String optionA, String optionB, String optionC,
                              String optionD, String key, String contestid) {
            this.quizzcontestid = quizzcontestid;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.key = key;
            this.contestid = contestid;
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

    }

    public static class TestResult{

        private String userInfoid;
        private String typeresultid;
        private String resultid;
        private String numOfCorrect;
        private String numOfIncorrect;
        private String times;
        private String totalTime;

        public TestResult(String userInfoid, String typeresultid, String resultid , String numOfCorrect, String numOfIncorrect, String times, String totalTime) {
            this.userInfoid = userInfoid;
            this.typeresultid = typeresultid;
            this.numOfCorrect = numOfCorrect;
            this.numOfIncorrect = numOfIncorrect;
            this.times = times;
            this.resultid = resultid;
            this.totalTime = totalTime;

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

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }
    }

    public static class TestResultUser extends TestResult{
        String description;
        String date;
        public TestResultUser(String description,  String numOfCorrect, String numOfIncorrect, String times, String totalTime, String date) {
            super("", "", "", numOfCorrect, numOfIncorrect, times, totalTime);
            this.description = description;
            this.date =date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }



}
