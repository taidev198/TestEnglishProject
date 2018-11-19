package controller;

import helper.LoadSceneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.TestGrammarModel;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by traig on 9:59 AM, 11/15/2018
 */
public class AdminUserController implements Initializable, LoadSceneHelper {
    @FXML
     TableView<UserModel.User> tableView;
     UserModel model;
    ObservableList<UserModel.User> data;
    @FXML
    Text filterText;
    List<MenuButton> menuButton;
    FilteredList<UserModel.User> filteredList;
    private  List<List<String>> listUser;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> idCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> usernameCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> passwordCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> firstnameCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> lastnameCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> addressCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> phoneCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> emailCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> birthCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, MenuButton> actionCol;

    //textFields
    @FXML
    TextField idText;
    @FXML
    TextField usernameText;
    @FXML
    TextField passwordText;
    @FXML
    TextField firstnameText;
    @FXML
    TextField lastnameText;
    @FXML
    TextField addressText;
    @FXML
    TextField phoneText;
    @FXML
    TextField emailText;
    @FXML
    TextField birthText;
    @FXML
    TextField searchingField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new UserModel();
        listUser = model.getUserInfo();
        idCol.setCellValueFactory(new PropertyValueFactory<>("userInfoid"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("menuButton"));
        menuButton = new ArrayList<>();
        data = FXCollections.observableArrayList();
        for (int i = 0; i < listUser.get(0).size(); i++) {
            menuButton.add(new MenuButton());
            MenuItem edit = new MenuItem("edit");
            MenuItem delete = new MenuItem("delete");
            menuButton.get(i).setText("...");
            menuButton.get(i).getItems().addAll(edit);
            menuButton.get(i).getItems().addAll(delete);
            int id = i;
            edit.setOnAction(event -> {
                editableCols();
                System.out.println("edit");
            });
            delete.setOnAction(event -> {
                System.out.println(id);
                removeSelectedRows(Integer.parseInt(listUser.get(0).get(id)), tableView.getSelectionModel().getSelectedIndex());
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            });

            data.addAll(new UserModel.User(listUser.get(0).get(i),
                    listUser.get(1).get(i), listUser.get(2).get(i),listUser.get(3).get(i),
                    listUser.get(4).get(i), listUser.get(5).get(i), listUser.get(6).get(i),
                    listUser.get(7).get(i), (listUser.get(8).get(i)) , menuButton.get(i)) );


        }
        tableView.setItems(data);
        tableView.setRowFactory(param -> new rowUser());
        filter();
    }

    private void filter() {
        filteredList = new FilteredList<>(tableView.getItems(), questionTableView -> true);
        searchingField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(user ->  {
                filterText.setVisible(false);
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getFirstname().contains(lowerCaseFilter))
                    return true;
                else return user.getLastname().contains(lowerCaseFilter);
            });
        });
        filterText.setOnKeyPressed(event ->{
            if (event.getEventType() != null)
                filterText.setVisible(false);
        } );

        SortedList<UserModel.User> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

    private void removeSelectedRows(int parseInt, int selectedIndex) {
        model.removeUser(parseInt);
        data.remove(selectedIndex);
    }

    private void editableCols() {

    }

    public void addRow(){
        menuButton.add(new MenuButton());
        MenuItem edit = new MenuItem("edit");
        MenuItem delete = new MenuItem("delete");
        edit.setOnAction(event -> {
            editableCols();
            System.out.println("edit");
        });
        delete.setOnAction(event -> {
            removeSelectedRows(Integer.parseInt((tableView.getSelectionModel().getSelectedItem().getUserInfoid())), tableView.getSelectionModel().getSelectedIndex());
        });
        menuButton.get(menuButton.size() -1).setText("...");
        menuButton.get(menuButton.size() -1).getItems().addAll(edit);
        menuButton.get(menuButton.size() -1).getItems().addAll(delete);
        data.addAll(new UserModel.User(
                idText.getText(),usernameText.getText(),passwordText.getText(),
                addressText.getText(),emailText.getText(),phoneText.getText(),firstnameText.getText(),lastnameText.getText(),
                birthText.getText(), menuButton.get(menuButton.size() -1))
        );
        model.addUser(new UserModel.User( idText.getText(),usernameText.getText(),passwordText.getText(),
                addressText.getText(),emailText.getText(),phoneText.getText(),firstnameText.getText(),lastnameText.getText(),
                birthText.getText(), menuButton.get(menuButton.size() -1)), false);

        idText.clear();
        usernameText.clear();
        passwordText.clear();
        firstnameText.clear();
        lastnameText.clear();
        addressText.clear();
        phoneText.clear();
        emailText.clear();
        birthText.clear();
    }
    @Override
    public void switchScene(String url, Object parent) {
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((AnchorPane) parent).getChildren().clear();
        ((AnchorPane) parent).getChildren().addAll(user);
    }

    @Override
    public void loadData() {

    }



    private static class rowUser extends TableRow<UserModel.User>{
        private Label userInfoid;
        private Label username;
        private Label password;
        private Label address ;
        private Label email ;
        private Label phone ;
        private Label firstname ;
        private Label lastname;
        private Label birth ;
        private MenuButton menuButton;
        public rowUser(){
            super();
            userInfoid = new Label();
            username = new Label();
            password = new Label();
            address = new Label();
            email = new Label();
            phone = new Label();
            firstname = new Label();
            lastname = new Label();
            birth =  new Label();
            menuButton = new MenuButton();
        }

        @Override
        protected void updateItem(UserModel.User item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !isEmpty()){
                userInfoid.setText(item.getUserInfoid());
                username.setText(item.getUsername());
                password.setText(item.getPassword());
                address.setText(item.getAddress());
                email.setText(item.getEmail());
                phone.setText(item.getPhone());
                firstname.setText(item.getFirstname());
                lastname.setText(item.getLastname());
                birth.setText(item.getBirth());
            }
        }
    }
}
