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
import model.QuizTestGrammarModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminQuizTestController implements Initializable {

    @FXML
    TableView<QuizTestGrammarModel.QuizzQuesstionView> tableView;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> idCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> questionCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> optionACol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> optionBCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> optionCCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> optionDCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> keyCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> contestidCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, MenuButton> actionCol;

    ObservableList<QuizTestGrammarModel.QuizzQuesstionView> data;

    @FXML
    TextField searchingField;
    @FXML
    Text filterText;
    List<MenuButton> menuButton;
    QuizTestGrammarModel model;
    FilteredList<QuizTestGrammarModel.QuizzQuesstionView> filteredList;
    private  List<List<String>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new QuizTestGrammarModel();
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("quizzcontestid"));
        questionCol.setCellValueFactory(
                new PropertyValueFactory<>("question"));
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

        actionCol.setCellValueFactory(
                new PropertyValueFactory<>("menuButton"));
        listQuestion = model.getDB();
        menuButton = new ArrayList<>();
        data = FXCollections.observableArrayList();
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
          addQuestion(i);
        }
        tableView.setItems(data);
        tableView.setRowFactory(param -> new rowQuizQuestion());
        filter();

    }

    private void addQuestion(int i){
        menuButton.add(new MenuButton());
        MenuItem edit = new MenuItem("EDIT");
        MenuItem delete = new MenuItem("DELETE");
        MenuItem add = new MenuItem("ADD");
        menuButton.get(i).setText("...");
        menuButton.get(i).getItems().add(add);
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
        add.setOnAction(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1)
                OnMessage("YOU HAVE NOT CHOSEN ANY ROW", Alert.AlertType.ERROR, "ERORR", "YOU HAVE ENTERED SOMETHINGS WRONG:");
            else {
                OnEdit(false);

            }
        });
        data.addAll(new QuizTestGrammarModel.QuizzQuesstionView(listQuestion.get(0).get(i),
                listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                listQuestion.get(4).get(i), listQuestion.get(5).get(i),
                listQuestion.get(6).get(i), (listQuestion.get(7).get(i)) , menuButton.get(i)) );


    }

    private void filter() {
        filteredList = new FilteredList<>(tableView.getItems(), quizzQuesstionView  -> true);
        searchingField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(quizzQuesstionView  -> {
                filterText.setVisible(false);
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (quizzQuesstionView.getQuestion().contains(lowerCaseFilter))
                    return true;
                else return quizzQuesstionView.getQuizzcontestid().contains(lowerCaseFilter);
            });
        });
        filterText.setOnKeyPressed(event ->{
            if (event.getEventType() != null)
                filterText.setVisible(false);
        } );

        SortedList<QuizTestGrammarModel.QuizzQuesstionView> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    private void removeSelectedRows(int parseInt, int selectedIndex) {
        model.removeQuizzTestGrammar(parseInt);
        data.remove(selectedIndex);
    }


    private static class rowQuizQuestion extends TableRow<QuizTestGrammarModel.QuizzQuesstionView>{
        private Label id;
        private Label question;
        private Label optionA;
        private Label optionB;
        private Label optionC;
        private Label optionD;
        private Label key;
        private Label contestid;
        private MenuButton action;
        public rowQuizQuestion(){
            super();
            id = new Label();
            question = new Label();
            optionA = new Label();
            optionB = new Label();
            optionC = new Label();
            optionD = new Label();
            key = new Label();
            contestid = new Label();
        }

        @Override
        protected void updateItem(QuizTestGrammarModel.QuizzQuesstionView item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                id.setText(String.valueOf(item.getQuizzcontestid()));
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                optionB.setText(item.getOptionB());
                optionC.setText(item.getOptionC());
                optionD.setText(item.getOptionD());
                key.setText(item.getKey());
                contestid.setText(item.getContestid());
            }else setGraphic(null);

        }
    }

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


        HBox questionHbox = new HBox(30);
        Label questionlabel = new Label("QUESTION");
        TextArea questionText = new TextArea();
        questionText.setPromptText("QUESTION");
        questionText.setPrefWidth(350);

        questionHbox.getChildren().addAll(questionlabel, questionText);

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


        HBox contestidHbox = new HBox(5);
        Label contestidlabel = new Label("CONTEST ID");
        TextField contestidText = new TextField();
        contestidText.setPromptText("CONTEST ID");
        contestidHbox.getChildren().addAll(contestidlabel, contestidText);
        int selectedIdx = tableView.getSelectionModel().getSelectedIndex();
        if (isEditable){
            idText.setText(listQuestion.get(0).get(selectedIdx));
            questionText.setText(listQuestion.get(1).get(selectedIdx));
            optionAText.setText(listQuestion.get(2).get(selectedIdx));
            optionBText.setText(listQuestion.get(3).get(selectedIdx));
            optionCText.setText(listQuestion.get(4).get(selectedIdx));
            optionDText.setText(listQuestion.get(5).get(selectedIdx));
            keyText.setText(listQuestion.get(6).get(selectedIdx));
            contestidText.setText(listQuestion.get(7).get(selectedIdx));
            listQuestion.get(0).set(selectedIdx, String.valueOf(-1));
        }

        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (isValid(idText.getText(), keyText.getText(),optionAText.getText(),
                            optionBText.getText(), optionCText.getText(), optionDText.getText(), true) &&
                            !isEmpty(idText.getText(), questionText.getText(), optionAText.getText(),
                                    optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                    keyText.getText())){
                        model.update(idText.getText(), new QuizTestGrammarModel.QuizzQuesstion(idText.getText(), questionText.getText(), optionAText.getText(),
                                optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                keyText.getText(), contestidText.getText()));
                        dialog.close();
                        listQuestion.get(0).set(selectedIdx, idText.getText());
                        listQuestion.get(1).set(selectedIdx, questionText.getText());
                        listQuestion.get(2).set(selectedIdx, optionAText.getText());
                        listQuestion.get(3).set(selectedIdx, optionBText.getText());
                        listQuestion.get(4).set(selectedIdx, optionCText.getText());
                        listQuestion.get(5).set(selectedIdx, optionDText.getText());
                        listQuestion.get(6).set(selectedIdx, keyText.getText());
                        listQuestion.get(7).set(selectedIdx, contestidText.getText());
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");

                        data.set(selectedIdx,new QuizTestGrammarModel.QuizzQuesstionView(listQuestion.get(1).get(selectedIdx), listQuestion.get(2).get(selectedIdx),listQuestion.get(3).get(selectedIdx),
                                listQuestion.get(4).get(selectedIdx), listQuestion.get(5).get(selectedIdx),
                                listQuestion.get(6).get(selectedIdx), (listQuestion.get(0).get(selectedIdx)), listQuestion.get(7).get(selectedIdx) , menuButton.get(selectedIdx)) );

                    }
                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (isValid(idText.getText(),  keyText.getText(),optionAText.getText(),
                            optionBText.getText(), optionCText.getText(), optionDText.getText(), false) &&
                            !isEmpty(idText.getText(), questionText.getText(), optionAText.getText(),
                                    optionBText.getText(), optionCText.getText(), optionDText.getText(),
                                    keyText.getText())){
                        QuizTestGrammarModel.QuizzQuesstionView quizzQuesstionView =
                                new QuizTestGrammarModel.QuizzQuesstionView(
                                        questionText.getText(),questionText.getText(),optionAText.getText(),
                                        optionAText.getText(),optionCText.getText(),optionDText.getText(),keyText.getText(), contestidText.getText(), menuButton.get(selectedIdx));
                        listQuestion.get(0).add(idText.getText());
                        listQuestion.get(1).add(questionText.getText());
                        listQuestion.get(2).add(optionAText.getText());
                        listQuestion.get(3).add(optionBText.getText());
                        listQuestion.get(4).add(optionCText.getText());
                        listQuestion.get(5).add(optionDText.getText());
                        listQuestion.get(6).add(keyText.getText());
                        listQuestion.get(7).add(contestidText.getText());
                        data.add(quizzQuesstionView);
                        addQuestion(selectedIdx);
                        model.addNewTestGrammar(quizzQuesstionView);
                        OnMessage("", Alert.AlertType.INFORMATION, "SUCCESS", "DONE!");
                        questionText.clear();
                        idText.clear();
                        optionAText.clear();
                        optionBText.clear();
                        optionCText.clear();
                        optionDText.clear();
                        contestidText.clear();
                        keyText.clear();
                    }

                });
        dialogVbox1.getChildren().addAll(idHbox);
        dialogVbox1.getChildren().addAll(questionHbox);
        dialogVbox1.getChildren().addAll(optionAHbox);
        dialogVbox1.getChildren().addAll(optionBHbox);
        dialogVbox1.getChildren().addAll(optionCHbox);
        dialogVbox1.getChildren().addAll(optionDHbox);
        dialogVbox1.getChildren().addAll(keyHbox);
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
