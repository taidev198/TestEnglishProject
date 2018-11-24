package controller;

import helper.LoadSceneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TestGrammarModel;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
            MenuItem edit = new MenuItem("EDIT");
            MenuItem delete = new MenuItem("DELETE");
            MenuItem add = new MenuItem("ADD");
            menuButton.get(i).setText("...");
            menuButton.get(i).getItems().addAll(edit);
            menuButton.get(i).getItems().addAll(add);
            menuButton.get(i).getItems().addAll(delete);
            int id = i;
            add.setOnAction(event -> {

                if (tableView.getSelectionModel().getSelectedIndex() == -1)
                    OnMessage("YOU HAVE NOT CHOSEN ANY ROW", Alert.AlertType.ERROR, "ERORR", "YOU HAVE ENTERED SOMETHINGS WRONG:");
                else OnEdit(false);
            });

            edit.setOnAction(event -> {

                if (tableView.getSelectionModel().getSelectedIndex() == -1)
                    OnMessage("YOU HAVE NOT CHOSEN ANY ROW", Alert.AlertType.ERROR, "ERORR", "YOU HAVE ENTERED SOMETHINGS WRONG:");
                else
                    OnEdit(true);
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

    public void OnMessage(String message, Alert.AlertType type, String title, String header) {
        Alert alert  = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void OnEdit(boolean isEditable){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button update = new Button("UPDATE");
        Button add = new Button("ADD");
        //init piechart
        AnchorPane root = new AnchorPane();

        Label displayLabel = new Label("What do you want to do ?");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner(tableView.getScene().getWindow());

        VBox dialogVbox1 = new VBox(30);
        dialogVbox1.setAlignment(Pos.CENTER);
        dialogVbox1.setFillWidth(true);
        dialogVbox1.setSpacing(30);
        dialogVbox1.setPadding(new Insets(40, 0, 0, 80));

        HBox idHbox = new HBox(80);
        Label idlabel = new Label("ID");
        TextField idText = new TextField();
        idText.setPromptText("ID");
        idHbox.getChildren().addAll(idlabel, idText);


        HBox usernameHbox = new HBox(20);
        Label usernamelabel = new Label("USERNAME");
        TextField usernameText = new TextField();
        usernameText.setPromptText("USERNAME");
        usernameHbox.getChildren().addAll(usernamelabel, usernameText);

        HBox passwordHbox = new HBox(20);
        Label passwordlabel = new Label("PASSWORD");
        TextField passwordText = new TextField();
        passwordText.setPromptText("PASSWORD");
        passwordHbox.getChildren().addAll(passwordlabel, passwordText);

        HBox firstnameHbox = new HBox(20);
        Label firstnameLabel = new Label("FIRSTNAME");
        TextField firstnameText = new TextField();
        firstnameText.setPromptText("FIRSTNAME");
        firstnameHbox.getChildren().addAll(firstnameLabel, firstnameText);

        HBox lastnameHbox = new HBox(20);
        Label lastnameLabel = new Label("LASTNAME");
        TextField lastnameText = new TextField();
        lastnameText.setPromptText("LASTNAME");
        lastnameHbox.getChildren().addAll(lastnameLabel, lastnameText);

        HBox addressHbox = new HBox(30);
        Label addressLabel = new Label("ADDRESS");
        TextField addressText = new TextField();
        addressText.setPromptText("ADDRESS");

        addressHbox.getChildren().addAll(addressLabel, addressText);

        HBox phoneHbox = new HBox(42);
        Label phonelabel = new Label("PHONE");
        TextField phoneText = new TextField();
        phoneText.setPromptText("PHONE");

        phoneHbox.getChildren().addAll(phonelabel, phoneText);

        HBox emailHbox = new HBox(50);
        Label emaillabel = new Label("EMAIL");
        TextField emailText = new TextField();
        emailText.setPromptText("EMAIL");

        emailHbox.getChildren().addAll(emaillabel, emailText);

        HBox birthHbox = new HBox(50);
        Label birthLabel = new Label("BIRTH");
        DatePicker birthText = new DatePicker();
        birthText.setPromptText("BIRTH");

        birthHbox.getChildren().addAll(birthLabel, birthText);
        if (isEditable){
            int selectedIdx = tableView.getSelectionModel().getSelectedIndex();
            idText.setText(listUser.get(0).get(selectedIdx));
            usernameText.setText(listUser.get(1).get(selectedIdx));
            passwordText.setText(listUser.get(2).get(selectedIdx));
            addressText.setText(listUser.get(3).get(selectedIdx));
            emailText.setText(listUser.get(4).get(selectedIdx));
            phoneText.setText(listUser.get(5).get(selectedIdx));
            firstnameText.setText(listUser.get(6).get(selectedIdx));
            lastnameText.setText(listUser.get(7).get(selectedIdx));
            Queue<Integer> queue = new LinkedList<>();
            ((LinkedList<Integer>) queue).addAll(queue.size()-1, new ArrayList<>());
        }


        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {


                });
        dialogVbox1.getChildren().addAll(idHbox);
        dialogVbox1.getChildren().addAll(usernameHbox);
        dialogVbox1.getChildren().addAll(passwordHbox);
        dialogVbox1.getChildren().addAll(firstnameHbox);
        dialogVbox1.getChildren().addAll(lastnameHbox);
        dialogVbox1.getChildren().addAll(addressHbox);
        dialogVbox1.getChildren().addAll(phoneHbox);
        dialogVbox1.getChildren().addAll(emailHbox);
        dialogVbox1.getChildren().addAll(birthHbox);
        if (isEditable)
            dialogVbox1.getChildren().addAll(update);
        else dialogVbox1.getChildren().addAll(add);


        root.getChildren().addAll(dialogVbox1);
        Scene dialogScene = new Scene(root, 500, 700);
        root.setStyle(
                "   -fx-background-color: rgb(58,69,88);\n" +
                        "    -fx-background-radius: 0px;\n" +
                        "    -fx-text-fill: #b8b1b1;\n");
        update.setStyle("-fx-background-color: rgb(22,169,250);\n" +
                "    -fx-background-radius: 0px;\n" +
                "    -fx-text-fill: #0099ff;");
        add.setStyle("-fx-background-color: rgb(22,169,250);\n" +
                "    -fx-background-radius: 0px;\n" +
                "    -fx-text-fill: #0099ff;");
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
