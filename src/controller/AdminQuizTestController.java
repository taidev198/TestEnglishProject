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
import model.TestGrammarModel;

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
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, String> grammarIdCol;
    @FXML
    TableColumn<QuizTestGrammarModel.QuizzQuesstionView, MenuButton> actionCol;

    ObservableList<QuizTestGrammarModel.QuizzQuesstionView> data;

    @FXML
    TextField idText;
    @FXML
    TextField questionText;
    @FXML
    TextField optionAText;
    @FXML
    TextField optionBText;
    @FXML
    TextField optionCText;
    @FXML
    TextField optionDText;
    @FXML
    TextField keyText;
    @FXML
    TextField contestidText;
    @FXML
    TextField grammarIdText;
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
        grammarIdCol.setCellValueFactory(
                new PropertyValueFactory<>("grammarid"));

        actionCol.setCellValueFactory(
                new PropertyValueFactory<>("menuButton"));
        listQuestion = model.getDB();
        menuButton = new ArrayList<>();
        data = FXCollections.observableArrayList();
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            menuButton.add(new MenuButton());
            MenuItem edit = new MenuItem("edit");
            MenuItem delete = new MenuItem("delete");
            menuButton.get(i).setText("...");
            menuButton.get(i).getItems().addAll(edit);
            menuButton.get(i).getItems().addAll(delete);
            int id = i;
            edit.setOnAction(event -> {
                OnEdit(false);
                System.out.println("edit");
            });
            delete.setOnAction(event -> {
                System.out.println(id);
                removeSelectedRows(Integer.parseInt(listQuestion.get(0).get(id)), tableView.getSelectionModel().getSelectedIndex());
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            });

            data.addAll(new QuizTestGrammarModel.QuizzQuesstionView(listQuestion.get(0).get(i),
                    listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                    listQuestion.get(4).get(i), listQuestion.get(5).get(i),
                    listQuestion.get(6).get(i), (listQuestion.get(7).get(i)), (listQuestion.get(8).get(i)) , menuButton.get(i)) );


        }
        tableView.setItems(data);
        tableView.setRowFactory(param -> new rowQuizzQuestion());
        filter();

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
            removeSelectedRows(Integer.parseInt((tableView.getSelectionModel().getSelectedItem().getQuizzcontestid())), tableView.getSelectionModel().getSelectedIndex());
        });
        menuButton.get(menuButton.size() -1).setText("...");
        menuButton.get(menuButton.size() -1).getItems().addAll(edit);
        menuButton.get(menuButton.size() -1).getItems().addAll(delete);
        data.addAll(new QuizTestGrammarModel.QuizzQuesstionView(
                idText.getText(),questionText.getText(),optionAText.getText(),
                optionBText.getText(),optionCText.getText(),optionDText.getText(),keyText.getText(),contestidText.getText(), grammarIdText.getText(),
                menuButton.get(menuButton.size() -1))
        );
        model.addNewTestGrammar(new QuizTestGrammarModel.QuizzQuesstionView(
                idText.getText(),questionText.getText(),optionAText.getText(),
                optionBText.getText(),optionCText.getText(),optionDText.getText(),keyText.getText(),
                contestidText.getText(), grammarIdText.getText(), menuButton.get(menuButton.size() -1))
        );

        questionText.clear();
        optionAText.clear();
        optionBText.clear();
        optionCText.clear();
        optionDText.clear();
        grammarIdText.clear();
        contestidText.clear();
        idText.clear();
        keyText.clear();

    }

    private static class rowQuizzQuestion extends TableRow<QuizTestGrammarModel.QuizzQuesstionView>{
        private Label id;
        private Label question;
        private Label optionA;
        private Label optionB;
        private Label optionC;
        private Label optionD;
        private Label key;
        private Label contestid;
        private Label grammarid;
        private MenuButton action;
        public rowQuizzQuestion(){
            super();
            id = new Label();
            question = new Label();
            optionA = new Label();
            optionB = new Label();
            optionC = new Label();
            optionD = new Label();
            key = new Label();
            grammarid = new Label();
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
                grammarid.setText(item.getGrammarid());
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

        HBox grammaridHbox = new HBox(5);
        Label grammaridlabel = new Label("GRAMMAR ID");
        TextField grammaridText = new TextField();
        grammarIdText.setPromptText("GRAMMAR ID");
        grammaridHbox.getChildren().addAll(grammaridlabel, grammaridText);

        HBox contestidHbox = new HBox(5);
        Label contestidlabel = new Label("CONTEST ID");
        TextField contestidText = new TextField();
        contestidText.setPromptText("CONTEST ID");
        contestidHbox.getChildren().addAll(contestidlabel, contestidText);

        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {


                });
        dialogVbox1.getChildren().addAll(idHbox);
        dialogVbox1.getChildren().addAll(questionHbox);
        dialogVbox1.getChildren().addAll(optionAHbox);
        dialogVbox1.getChildren().addAll(optionBHbox);
        dialogVbox1.getChildren().addAll(optionCHbox);
        dialogVbox1.getChildren().addAll(optionDHbox);
        dialogVbox1.getChildren().addAll(keyHbox);
        dialogVbox1.getChildren().addAll(grammaridHbox);
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
}
