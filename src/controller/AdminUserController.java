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
import model.QuizTestModel;
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
//    @FXML
//    TableView<QuizTestModel.TestResultUser> resultTableView;
     UserModel model;
    ObservableList<UserModel.User> data;
    @FXML
    Text filterText;
    List<MenuButton> menuButton;
    FilteredList<UserModel.User> filteredList;
    private  List<List<String>> listUser;

    //result
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> contestCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> grammarCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> numOfCorrectCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> numOfIncorrectCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> timesCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> totalTimeCol;
//    @FXML
//    TableColumn<QuizTestModel.TestResultUser, String> dateCol;


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
    @FXML
    TextField searchingField;
    @FXML
    Button addBtn;
    List<List<String>> listResultsContest ;
    QuizTestModel quizTestModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new UserModel();
        quizTestModel = new QuizTestModel();
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
        addBtn.setOnMouseClicked(event -> OnEdit(false));

        for (int i = 0; i < listUser.get(0).size(); i++) {
            menuButton.add(new MenuButton());
            MenuItem edit = new MenuItem("EDIT");
            MenuItem delete = new MenuItem("DELETE");
            MenuItem result = new MenuItem("RESULT");
            menuButton.get(i).setText("...");
            menuButton.get(i).getItems().addAll(edit, delete, result);
            int id = i;
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

            result.setOnAction(event -> {
                Stage stage = new Stage();
                stage.setTitle("RESULT TEST OF USERID :" + Integer.parseInt(listUser.get(0).get(id)));
                stage.setWidth(1020);
                stage.setHeight(449);
                Scene scene = new Scene(new AnchorPane());

                TableView<QuizTestModel.TestResultUser> resultTableView = new TableView<>();

                TableColumn<QuizTestModel.TestResultUser, String> contestCol = new TableColumn<>("CONTEST");
                contestCol.setPrefWidth(268.79999351501465);
                contestCol.setStyle( "-fx-alignment: CENTER;");
                TableColumn<QuizTestModel.TestResultUser, String> numOfCorrectCol = new TableColumn<>("NUM OF CORRECT");
                numOfCorrectCol.setPrefWidth(150);
                numOfCorrectCol.setStyle( "-fx-alignment: CENTER;");
                TableColumn<QuizTestModel.TestResultUser, String> numOfIncorrectCol = new TableColumn<>("NUM OF INCORRECT");
                numOfIncorrectCol.setPrefWidth(160);
                numOfIncorrectCol.setStyle( "-fx-alignment: CENTER;");
                TableColumn<QuizTestModel.TestResultUser, String> timesCol = new TableColumn<>("TIMES");
                timesCol.setPrefWidth(102.4000244140625);
                timesCol.setStyle( "-fx-alignment: CENTER;");
                TableColumn<QuizTestModel.TestResultUser, String> totalTimeCol = new TableColumn<>("TOTAL TIME");
                totalTimeCol.setPrefWidth(115.2000732421875);
                totalTimeCol.setStyle( "-fx-alignment: CENTER;");
                TableColumn<QuizTestModel.TestResultUser, String> dateCol = new TableColumn<>("DATE");
                dateCol.setPrefWidth(200);
                dateCol.setStyle( "-fx-alignment: CENTER;");
                contestCol.setCellValueFactory(new PropertyValueFactory<>("description"));
//                grammarCol.setCellValueFactory(new PropertyValueFactory<>("grammar"));
                numOfCorrectCol.setCellValueFactory(new PropertyValueFactory<>("numOfCorrect"));
                numOfIncorrectCol.setCellValueFactory(new PropertyValueFactory<>("numOfIncorrect"));
                timesCol.setCellValueFactory(new PropertyValueFactory<>("times"));
                totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
                dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));


                 ObservableList<QuizTestModel.TestResultUser> data =
                        FXCollections.observableArrayList();
                listResultsContest = quizTestModel.getResultByAdmin(Integer.parseInt(listUser.get(0).get(id)));
                System.out.println(listResultsContest);
                if (listResultsContest.size() > 0){
                    for (int j = 0; j < listResultsContest.get(0).size(); j++) {
                        data.add(new QuizTestModel.TestResultUser(listResultsContest.get(0).get(j), listResultsContest.get(1).get(j),
                                listResultsContest.get(2).get(j), listResultsContest.get(3).get(j), listResultsContest.get(4).get(j),
                                listResultsContest.get(5).get(j)));
                    }
                }

                resultTableView.getColumns().addAll(contestCol, numOfCorrectCol, numOfIncorrectCol,
                        timesCol, totalTimeCol, dateCol);
                resultTableView.setItems(data);
                resultTableView.setPrefWidth(1000);
                resultTableView.setPrefHeight(200);
                resultTableView.setLayoutX(7);
                resultTableView.setLayoutY(24);
                ((AnchorPane) scene.getRoot()).getChildren().addAll(resultTableView);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
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
        }
        int selectedIdx = tableView.getSelectionModel().getSelectedIndex();
        String ordinaryId = idText.getText();
        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                    if (isValid(usernameText.getText(), passwordText.getText(), firstnameText.getText(), lastnameText.getText(), idText.getText(), false)){
                        model.update( new UserModel.User(idText.getText(), usernameText.getText(), passwordText.getText(),
                                addressText.getText(), emailText.getText(), phoneText.getText(),
                                firstnameText.getText(),lastnameText.getText(),"1990-12-31", menuButton.get(selectedIdx)), Integer.parseInt(ordinaryId));
                        dialog.close();
                        listUser.get(0).set(selectedIdx, idText.getText());
                        listUser.get(1).set(selectedIdx, usernameText.getText());
                        listUser.get(2).set(selectedIdx, passwordText.getText());
                        listUser.get(3).set(selectedIdx, addressText.getText());
                        listUser.get(4).set(selectedIdx, emailText.getText());
                        listUser.get(5).set(selectedIdx, phoneText.getText());
                        listUser.get(6).set(selectedIdx, firstnameText.getText());
                        listUser.get(7).set(selectedIdx, lastnameText.getText());
                        listUser.get(8).set(selectedIdx, "1990-12-31");
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");
                        data.set(selectedIdx,new UserModel.User(idText.getText(), usernameText.getText(), passwordText.getText(),
                                addressText.getText(), emailText.getText(), phoneText.getText(),
                                firstnameText.getText(),lastnameText.getText(),"1990-12-31", menuButton.get(selectedIdx)));
                    }
                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                    if (isValid(usernameText.getText(), passwordText.getText(), firstnameText.getText(), lastnameText.getText(), idText.getText(), false)){
                        menuButton.add(new MenuButton());
                        MenuItem edit = new MenuItem("EDIT");
                        MenuItem delete = new MenuItem("DELETE");
                        MenuItem result = new MenuItem("RESULT");
                        menuButton.get(menuButton.size()-1).setText("...");
                        menuButton.get(menuButton.size()-1).getItems().addAll(edit, delete, result);
                        edit.setOnAction(event -> {
                            if (tableView.getSelectionModel().getSelectedIndex() == -1)
                                OnMessage("YOU HAVE NOT CHOSEN ANY ROW", Alert.AlertType.ERROR, "ERORR", "YOU HAVE ENTERED SOMETHINGS WRONG:");
                            else {
                                OnEdit(true);
                            }
                        });
                        delete.setOnAction(event -> {
                            if (tableView.getSelectionModel().getSelectedIndex() == -1)
                                OnMessage("YOU HAVE NOT CHOSEN ANY ROW", Alert.AlertType.ERROR, "ERORR", "YOU HAVE ENTERED SOMETHINGS WRONG:");
                            else {
                                removeSelectedRows(Integer.parseInt(listUser.get(0).get(data.size() -1)), data.size() -1);
                            }
                        });
                        UserModel.User user =
                                new UserModel.User(idText.getText(), usernameText.getText(), passwordText.getText(),
                                        addressText.getText(), emailText.getText(), phoneText.getText(),
                                        firstnameText.getText(),lastnameText.getText(),"1990-12-31", menuButton.get(menuButton.size()-1));
                        listUser.get(0).add(idText.getText());
                        listUser.get(1).add(usernameText.getText());
                        listUser.get(2).add(passwordText.getText());
                        listUser.get(3).add(addressText.getText());
                        listUser.get(4).add(emailText.getText());
                        listUser.get(5).add(phoneText.getText());
                        listUser.get(6).add(firstnameText.getText());
                        listUser.get(7).add(lastnameText.getText());
                        listUser.get(8).add("1999-1-1");

                        model.addUser(user, false);
                        data.add( user);
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");
                        usernameText.clear();
                        idText.clear();
                        passwordText.clear();
                        phoneText.clear();
                        emailText.clear();
                        addressText.clear();
                        firstnameText.clear();
                        lastnameText.clear();
                    }
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
    public boolean isValid(String username, String password, String firstname, String lastname, String id,  boolean isEdit){
        if (!id.matches("[0-9]*")) {
            OnMessage("YOU MUST ENTER NUMBER ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return false;
        }
        if (!username.matches("[A-Za-z]*")) {
            OnMessage("YOU MUST ENTER TEXT ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return false;
        }

//        if (listUser.get(0).contains(id) && !isEdit){
//            OnMessage("YOU HAVE ENTER DUPLICATE ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");
//            return false;
//        }else if (listUser.get(0).contains(id) && isEdit){
//            OnMessage("YOU HAVE ENTER DUPLICATE ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");
//        }
//        if (!listQuestion.get(7).contains(grammarid)){
//            OnMessage("YOU HAVE ENTER INVALID GRAMMAR ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");
//            return false;
//        }
        return true;
    }

}
