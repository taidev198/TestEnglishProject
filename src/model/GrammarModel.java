package model;

import helper.ConnectDataHelper;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;

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

}
