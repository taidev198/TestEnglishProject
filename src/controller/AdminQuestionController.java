package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.QuestionModel;
import model.QuizTestGrammarModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminQuestionController implements Initializable {

    @FXML
    TableView<QuestionModel.QuestionTableView> tableView;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> idCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> questionCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> optionACol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> optionBCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> optionCCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> optionDCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> keyCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> contestidCol;

    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> grammaridCol;
    @FXML
    TableColumn<QuestionModel.QuestionTableView, String> typeidCol;

    @FXML
    TableColumn<QuestionModel.QuestionTableView, MenuButton> actionCol;
    @FXML
            Button addBtn;
    ObservableList<QuestionModel.QuestionTableView> data;

    @FXML
    TextField searchingField;
    @FXML
    Text filterText;
    List<MenuButton> menuButton;
    QuestionModel model;
    FilteredList<QuestionModel.QuestionTableView> filteredList;
    private  List<List<String>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new QuestionModel();
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("idquestion"));
        questionCol.setCellValueFactory(
                new PropertyValueFactory<>("content"));
        optionACol.setCellValueFactory(
                new PropertyValueFactory<>("optionA"));
        optionBCol.setCellValueFactory(
                new PropertyValueFactory<>("optionB"));
        optionCCol.setCellValueFactory(
                new PropertyValueFactory<>("optionC"));
        optionDCol.setCellValueFactory(
                new PropertyValueFactory<>("optionD"));
        keyCol.setCellValueFactory(
                new PropertyValueFactory<>("key"));
        contestidCol.setCellValueFactory(
                new PropertyValueFactory<>("contestid"));
        grammaridCol.setCellValueFactory(
                new PropertyValueFactory<>("grammarid"));
        typeidCol.setCellValueFactory(
                new PropertyValueFactory<>("typeid"));
        actionCol.setCellValueFactory(
                new PropertyValueFactory<>("action"));
        listQuestion = model.getQuestion();
        menuButton = new ArrayList<>();
        data = FXCollections.observableArrayList();
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
          addQuestion(i);
        }
        tableView.setItems(data);
        tableView.setRowFactory(param -> new rowQuestion());
        filter();
        addBtn.setOnMouseClicked(event -> {
            OnEdit(false);
        });

    }

    private void addQuestion(int i){
        menuButton.add(new MenuButton());
        MenuItem edit = new MenuItem("EDIT");
        MenuItem delete = new MenuItem("DELETE");
        menuButton.get(i).setText("...");
        menuButton.get(i).getItems().addAll(edit);
        menuButton.get(i).getItems().addAll(delete);
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
                removeSelectedRows(Integer.parseInt(listQuestion.get(0).get(i)), tableView.getSelectionModel().getSelectedIndex());
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            }

        });

        data.add(new QuestionModel.QuestionTableView(listQuestion.get(0).get(i),
                listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                listQuestion.get(4).get(i), listQuestion.get(5).get(i),
                listQuestion.get(6).get(i), (listQuestion.get(7).get(i)),
                listQuestion.get(8).get(i), (listQuestion.get(9).get(i)), menuButton.get(i)) );


    }

    private void filter() {
        filteredList = new FilteredList<>(tableView.getItems(), quizzQuesstionView  -> true);
        searchingField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(questionTableView -> {
                filterText.setVisible(false);
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (questionTableView.getContent().contains(lowerCaseFilter))
                    return true;
                else return questionTableView.getIdquestion().contains(lowerCaseFilter);
            });
        });
        filterText.setOnKeyPressed(event ->{
            if (event.getEventType() != null)
                filterText.setVisible(false);
        } );

        SortedList<QuestionModel.QuestionTableView> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    private void removeSelectedRows(int parseInt, int selectedIndex) {
        model.removeQuestion(parseInt);
        data.remove(selectedIndex);
    }


    private static class rowQuestion extends TableRow<QuestionModel.QuestionTableView>{
        private Label id;
        private Label question;
        private Label optionA;
        private Label optionB;
        private Label optionC;
        private Label optionD;
        private Label key;
        private Label contestid;
        private MenuButton action;
        public rowQuestion(){
            super();
            id = new Label();
            question = new Label();
            optionA = new Label();
            optionB = new Label();
            optionC = new Label();
            optionD = new Label();
            key = new Label();
            contestid = new Label();
            action = new MenuButton();
        }

        @Override
        protected void updateItem(QuestionModel.QuestionTableView item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                id.setText(String.valueOf(item.getIdquestion()));
                question.setText(item.getContent());
                optionA.setText(item.getOptionA());
                optionB.setText(item.getOptionB());
                optionC.setText(item.getOptionC());
                optionD.setText(item.getOptionD());
                key.setText(item.getKey());
                contestid.setText(item.getContestid());
            }else setGraphic(null);

        }
    }

    @FXML
    public void OnEdit(boolean isEditable){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button update = new Button("UPDATE");
        Button add = new Button("ADD");
        AnchorPane root = new AnchorPane();

        dialog.initModality(Modality.NONE);
        dialog.initOwner(tableView.getScene().getWindow());

        VBox dialogVbox1 = new VBox(30);
        dialogVbox1.setAlignment(Pos.CENTER);
        dialogVbox1.setFillWidth(true);
        dialogVbox1.setPadding(new Insets(40, 0, 0, 70));

        HBox idHbox = new HBox(90);
        Label idlabel = new Label("ID");
        TextField idText = new TextField();
        idText.setPromptText("ID");
        idHbox.getChildren().addAll(idlabel, idText);


        HBox contentHbox = new HBox(30);
        Label contentlabel = new Label("QUESTION");
        TextArea contentText = new TextArea();
        contentText.setPromptText("QUESTION");
        contentText.setPrefWidth(350);
        contentText.setPrefHeight(150);

        contentHbox.getChildren().addAll(contentlabel, contentText);

        HBox optionAHbox = new HBox(30);
        Label optionAlabel = new Label("OPTION A");
        TextField optionAText = new TextField();
        optionAText.setPromptText("OPTION A");
        optionAHbox.getChildren().addAll(optionAlabel, optionAText);

        HBox optionBHbox = new HBox(30);
        Label optionBlabel = new Label("OPTION B");
        TextField optionBText = new TextField();
        optionBText.setPromptText("OPTION B");
        optionBHbox.getChildren().addAll(optionBlabel, optionBText);

        HBox optionCHbox = new HBox(30);
        Label optionClabel = new Label("OPTION C");
        TextField optionCText = new TextField();
        optionCText.setPromptText("OPTION C");
        optionCHbox.getChildren().addAll(optionClabel, optionCText);

        HBox optionDHbox = new HBox(30);
        Label optionDlabel = new Label("OPTION D");
        TextField optionDText = new TextField();
        optionDText.setPromptText("OPTION D");
        optionDHbox.getChildren().addAll(optionDlabel, optionDText);

        HBox keyHbox = new HBox(70);
        Label keylabel = new Label("KEY");
        TextField keyText = new TextField();
        keyText.setPromptText("KEY");
        keyHbox.getChildren().addAll(keylabel, keyText);

        HBox grammaridHbox = new HBox(5);
        Label grammaridlabel = new Label("GRAMMAR ID");
        TextField grammaridText = new TextField();
        grammaridText.setPromptText("CONTEST ID");
        grammaridHbox.getChildren().addAll(grammaridlabel, grammaridText);


        HBox contestidHbox = new HBox(15);
        Label contestidlabel = new Label("CONTEST ID");
        TextField contestidText = new TextField();
        contestidText.setPromptText("CONTEST ID");
        contestidHbox.getChildren().addAll(contestidlabel, contestidText);

        HBox typeidHbox = new HBox(50);
        Label typeidlabel = new Label("TYPE ID");
        TextField typeidText = new TextField();
        typeidText.setPromptText("CONTEST ID");
        typeidHbox.getChildren().addAll(typeidlabel, typeidText);

        int selectedIdx = tableView.getSelectionModel().getSelectedIndex();
        if (isEditable){
            idText.setText(listQuestion.get(0).get(selectedIdx));
            contentText.setText(listQuestion.get(1).get(selectedIdx));
            optionAText.setText(listQuestion.get(2).get(selectedIdx));
            optionBText.setText(listQuestion.get(3).get(selectedIdx));
            optionCText.setText(listQuestion.get(4).get(selectedIdx));
            optionDText.setText(listQuestion.get(5).get(selectedIdx));
            keyText.setText(listQuestion.get(6).get(selectedIdx));
            grammaridText.setText(listQuestion.get(7).get(selectedIdx));
            contestidText.setText(listQuestion.get(8).get(selectedIdx));
            typeidText.setText(listQuestion.get(9).get(selectedIdx));
//            listQuestion.get(0).set(selectedIdx, String.valueOf(-1));
        }
        String ordinaryId = idText.getText();
        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (isValid(idText.getText(), keyText.getText(),optionAText.getText(),
                            optionBText.getText(), optionCText.getText(), optionDText.getText(), true) &&
                            !isEmpty(idText.getText(), contentText.getText(), optionAText.getText(),
                                    optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                    keyText.getText())){
                        model.update(ordinaryId, new QuestionModel.QuestionTableView(idText.getText(), contentText.getText(), optionAText.getText(),
                                optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                keyText.getText(),grammaridText.getText(), contestidText.getText(), typeidText.getText(), menuButton.get(selectedIdx)));
                        dialog.close();
                        listQuestion.get(0).set(selectedIdx, idText.getText());
                        listQuestion.get(1).set(selectedIdx, contentText.getText());
                        listQuestion.get(2).set(selectedIdx, optionAText.getText());
                        listQuestion.get(3).set(selectedIdx, optionBText.getText());
                        listQuestion.get(4).set(selectedIdx, optionCText.getText());
                        listQuestion.get(5).set(selectedIdx, optionDText.getText());
                        listQuestion.get(6).set(selectedIdx, keyText.getText());
                        listQuestion.get(7).set(selectedIdx, grammaridText.getText());
                        listQuestion.get(8).set(selectedIdx, contestidText.getText());
                        listQuestion.get(9).set(selectedIdx, typeidText.getText());
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");
                        data.set(selectedIdx, new QuestionModel.QuestionTableView(idText.getText(), contentText.getText(), optionAText.getText(),
                                optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                keyText.getText(),grammaridText.getText(), contestidText.getText(), typeidText.getText(), menuButton.get(selectedIdx)));

                    }
                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (isValid(idText.getText(),  keyText.getText(),optionAText.getText(),
                            optionBText.getText(), optionCText.getText(), optionDText.getText(), false) &&
                            !isEmpty(idText.getText(), contentText.getText(), optionAText.getText(),
                                    optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                    keyText.getText())){
                        menuButton.add(new MenuButton());
                        MenuItem edit = new MenuItem("EDIT");
                        MenuItem delete = new MenuItem("DELETE");
                        menuButton.get(menuButton.size()-1).setText("...");
                        menuButton.get(menuButton.size()-1).getItems().addAll(edit, delete);
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
                                removeSelectedRows(Integer.parseInt(listQuestion.get(0).get(data.size() -1)), data.size() -1);
                            }
                        });
                        QuestionModel.QuestionTableView questionTableView =
                                new  QuestionModel.QuestionTableView(idText.getText(), contentText.getText(),optionAText.getText(),
                                        optionBText.getText(),optionCText.getText(),optionDText.getText(),keyText.getText(),grammaridText.getText(), contestidText.getText(), typeidText.getText(), menuButton.get(menuButton.size()-1));
                        listQuestion.get(0).add(idText.getText());
                        listQuestion.get(1).add(contentText.getText());
                        listQuestion.get(2).add(optionAText.getText());
                        listQuestion.get(3).add(optionBText.getText());
                        listQuestion.get(4).add(optionCText.getText());
                        listQuestion.get(5).add(optionDText.getText());
                        listQuestion.get(6).add(keyText.getText());
                        listQuestion.get(7).add(grammaridText.getText());
                        listQuestion.get(8).add(contestidText.getText());
                        listQuestion.get(9).add(typeidText.getText());
                        model.addNewQuestion(questionTableView);
                        data.add( questionTableView);
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");
                        contentText.clear();
                        idText.clear();
                        optionAText.clear();
                        optionBText.clear();
                        optionCText.clear();
                        optionDText.clear();
                        contestidText.clear();
                        keyText.clear();
                        typeidText.clear();
                        grammaridText.clear();
                    }

                });
        dialogVbox1.getChildren().addAll(idHbox);
        dialogVbox1.getChildren().addAll(contentHbox);
        dialogVbox1.getChildren().addAll(optionAHbox);
        dialogVbox1.getChildren().addAll(optionBHbox);
        dialogVbox1.getChildren().addAll(optionCHbox);
        dialogVbox1.getChildren().addAll(optionDHbox);
        dialogVbox1.getChildren().addAll(keyHbox);
        dialogVbox1.getChildren().addAll(grammaridHbox);
        dialogVbox1.getChildren().addAll(contestidHbox);
        dialogVbox1.getChildren().addAll(typeidHbox);
        if (isEditable)
            dialogVbox1.getChildren().addAll(update);
        else dialogVbox1.getChildren().addAll(add);


        root.getChildren().addAll(dialogVbox1);
        Scene dialogScene = new Scene(root, 600, 800);
        dialog.setScene(dialogScene);
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
        dialog.show();
    }

    public void OnMessage(String message, Alert.AlertType type, String title, String header) {
        Alert alert  = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean isValid(String id, String key, String optionA,
                           String optionB, String optionC, String optionD, boolean isEdit){
        if (!id.matches("[0-9]*")) {
            OnMessage("YOU MUST ENTER NUMBER ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return false;
        }
        if (!key.matches("[A-D]")) {
            OnMessage("YOU MUST ENTER A, B, C OR D ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return false;
        }

        if (listQuestion.get(0).contains(id) && !isEdit){
            OnMessage("YOU HAVE ENTER DUPLICATE ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return false;
        }else if (listQuestion.get(0).contains(id) && isEdit){
            OnMessage("YOU HAVE ENTER DUPLICATE ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");

            return false;
        }
//        if (!listQuestion.get(7).contains(grammarid)){
//            OnMessage("YOU HAVE ENTER INVALID GRAMMAR ID ", Alert.AlertType.ERROR, "ERROR", "ERROR");
//            return false;
//        }
        return true;
    }

    public boolean isEmpty(String id, String question, String optionA, String optionB,
                           String optionC, String optionD, String key){
        if (id.equals("")){
            OnMessage("YOU HAVE NOT ENTERED ID YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (question.equals("")){
            OnMessage("YOU HAVE NOT ENTERED QUESTION YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (optionA.equals("")){
            OnMessage("YOU HAVE NOT ENTERED OPTION A YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (optionB.equals("")){
            OnMessage("YOU HAVE NOT ENTERED OPTION B YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (optionC.equals("")){
            OnMessage("YOU HAVE NOT ENTERED OPTION C YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (optionD.equals("")){
            OnMessage("YOU HAVE NOT ENTERED OPTION D YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }else if (key.equals("")){
            OnMessage("YOU HAVE NOT ENTERED KEY YET ", Alert.AlertType.ERROR, "ERROR", "ERROR");
            return true;
        }
        return false;
    }
}
