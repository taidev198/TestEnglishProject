package model;

import helper.ConnectDataHelper;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GrammarModel  {
    private ConnectDataHelper connectDataHelper;


    public GrammarModel() {
        connectDataHelper = new ConnectDataHelper("jdbc:mysql://localhost/data");
        try {
            connectDataHelper.connectDB();

        } catch (ClassNotFoundException | IllegalAccessException | SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        connectDataHelper.closeDB();
    }

    private ResultSet loadDB() throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {


         return connectDataHelper.getDB();
    }


    private void addGrammar(String description, String content){
        String query = "insert grammar(description, content) values(?, ?)";
        try(PreparedStatement statement = connectDataHelper.getConnection().prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, description);
            statement.setString(2, content);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteGrammar(int id){
        String query = "delete from grammar where id = ?";
        try(PreparedStatement statement = connectDataHelper.getConnection().prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<List<String>> getGrammar(){
        String query = "select * from grammar";
        List<List<String>> ans = new ArrayList<>();
        try(Statement statement = connectDataHelper.connectDB().createStatement()) {

            statement.execute("use data");
            ResultSet resultSet = statement.executeQuery(query);
            List<String> des = new ArrayList<>();
            List<String> content = new ArrayList<>();
            while (resultSet.next()){
                des.add(resultSet.getString("description"));
                content.add(resultSet.getString("content"));
            }
            ans.add(des);
            ans.add(content);
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return ans;

    }

}
