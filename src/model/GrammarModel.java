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

    public GrammarModel() {

    }

    public void addGrammar(String description, String content){
        String query = "insert grammar(description, content) values(?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.connectDB().prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, description);
            statement.setString(2, content);
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrammar(int id){
        String query = "delete from grammar where id = ?";
        try(PreparedStatement statement = ConnectDataHelper.connectDB().prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public List<List<String>> getGrammar(){
        String query = "select * from grammar";
        List<List<String>> ans = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.connectDB().createStatement()) {

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
