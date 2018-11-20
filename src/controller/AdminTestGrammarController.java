package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import model.TestGrammarModel;
import sun.security.krb5.internal.PAData;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**How to fix bug at TableColumn<nameClass, Integer> but TableView using String in column
 * https://stackoverflow.com/questions/27468546/how-to-use-simpleintegerproperty-in-javafx</>
 * -editable tableview with dynamic :http://java-buddy.blogspot.com/2013/03/javafx-editable-tableview-with-dynamic.html
 * --- auto commit when user click outside :https://stackoverflow.com/questions/23632884/how-to-commit-when-clicking-outside-an-editable-tableview-cell-in-javafx
 * --filter :https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering
 * Set Style for scene:https://www.programcreek.com/java-api-examples/?class=javafx.scene.layout.BorderPane&method=setStyle/
 * */
public class AdminTestGrammarController implements Initializable {

    @FXML
    TableView<TestGrammarModel.QuestionTableView> tableView;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> idCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> questionCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionACol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionBCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionCCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionDCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> keyCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> grammarIdCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, MenuButton> actionCol;

    ObservableList<TestGrammarModel.QuestionTableView> data;

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
    TextField grammarIdText;
    @FXML
    TextField searchingField;
    @FXML
    Text filterText;
    List<MenuButton> menuButton;
    TestGrammarModel model;
    FilteredList<TestGrammarModel.QuestionTableView> filteredList;
    private  List<List<String>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new TestGrammarModel();

        //set celCellValueFactory for each cols.
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("number"));
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
        grammarIdCol.setCellValueFactory(
                new PropertyValueFactory<>("grammarid"));

        actionCol.setCellValueFactory(
                new PropertyValueFactory<>("action"));

        listQuestion = model.getTestGrammar();
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
                editableCols();
                System.out.println("edit");
                OnEdit1(false);
            });
            delete.setOnAction(event -> {
                System.out.println(id);
                removeSelectedRows(Integer.parseInt(listQuestion.get(0).get(id)), tableView.getSelectionModel().getSelectedIndex());
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            });

            data.addAll(new TestGrammarModel.QuestionTableView(listQuestion.get(1).get(i),
                    listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                    listQuestion.get(4).get(i), listQuestion.get(5).get(i), "",
                    listQuestion.get(6).get(i), (listQuestion.get(0).get(i)), listQuestion.get(7).get(i) , menuButton.get(i)) );


        }
        tableView.setItems(data);
        tableView.setRowFactory(param -> new rowQuestion());
        filter();
    }

    private void filter() {
        filteredList = new FilteredList<>(tableView.getItems(), questionTableView -> true);
        searchingField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(questionTableView -> {
                filterText.setVisible(false);
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (questionTableView.getQuestion().contains(lowerCaseFilter))
                    return true;
                else return questionTableView.getNumber().contains(lowerCaseFilter);
            });
        });
        filterText.setOnKeyPressed(event ->{
            if (event.getEventType() != null)
                filterText.setVisible(false);
        } );

        SortedList<TestGrammarModel.QuestionTableView> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

    private void editableCols(){
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        idCol.setOnEditCommit(event -> event.getTableView().getItems().
//                    get(event.getTablePosition().getRow()).setNumber(event.getNewValue()));
        tableView.setEditable(true);
    }



    /**
     * Remove all selected rows.
     * no params.
     */
    public void removeSelectedRows(int id, int idx) {
        model.removeTestGrammar(id);
        data.remove(idx);
        // table selects by index, so we have to clear the selection or else items with that index would be selected
    }

    @FXML
    public void addRow(){

        menuButton.add(new MenuButton());
        MenuItem edit = new MenuItem("edit");
        MenuItem delete = new MenuItem("delete");
        edit.setOnAction(event -> {
            editableCols();
            System.out.println("edit");
        });
        delete.setOnAction(event -> {
            removeSelectedRows(Integer.parseInt((tableView.getSelectionModel().getSelectedItem().getNumber())), tableView.getSelectionModel().getSelectedIndex());
        });
        menuButton.get(menuButton.size() -1).setText("...");
        menuButton.get(menuButton.size() -1).getItems().addAll(edit);
        menuButton.get(menuButton.size() -1).getItems().addAll(delete);
        data.addAll(new TestGrammarModel.QuestionTableView(
                questionText.getText(),questionText.getText(),optionAText.getText(),
                optionAText.getText(),optionCText.getText(),optionDText.getText(),"",keyText.getText(),
                idText.getText(), grammarIdText.getText(), menuButton.get(menuButton.size() -1))
        );
        model.addNewTestGrammar(new TestGrammarModel.QuestionTableView(
                questionText.getText(),questionText.getText(),optionAText.getText(),
                optionAText.getText(),optionCText.getText(),optionDText.getText(),"",keyText.getText(),
                idText.getText(), grammarIdText.getText(), menuButton.get(menuButton.size() -1))
        );

        questionText.clear();
        optionAText.clear();
        optionBText.clear();
        optionCText.clear();
        optionDText.clear();
        grammarIdText.clear();
        idText.clear();
        keyText.clear();

    }

    public static class rowQuestion extends TableRow<TestGrammarModel.QuestionTableView>{

        private Label id;
        private Label question;
        private Label optionA;
        private Label optionB;
        private Label optionC;
        private Label optionD;
        private Label key;
        private Label grammarid;
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
            grammarid = new Label();
        }

        @Override
        protected void updateItem(TestGrammarModel.QuestionTableView item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                id.setText(String.valueOf(item.getNumber()));
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                optionB.setText(item.getOptionB());
                optionC.setText(item.getOptionC());
                optionD.setText(item.getOptionD());
                key.setText(item.getKey());
                grammarid.setText(item.getAns());
            }else setGraphic(null);

        }

        @Override
        public void commitEdit(TestGrammarModel.QuestionTableView newValue) {
            TestGrammarModel.QuestionTableView currentItem = getItem();
            if (currentItem == null || newValue == null || !Objects.equals(newValue, currentItem)) {
                super.commitEdit(newValue);
            } else {
                // if the string is the same, keep the old value
                super.commitEdit(currentItem);
            }
        }
    }

    public void OnEdit(boolean isEditable){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button update = new Button("UPDATE");
        //update.setPadding(new Insets(0, 100,0, 0));
        Button add = new Button("ADD");
        //init piechart
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

    public void OnEdit1(boolean isEditable){
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
        grammaridText.setPromptText("GRAMMAR ID");
        grammaridHbox.getChildren().addAll(grammaridlabel, grammaridText);

        HBox contestidHbox = new HBox(15);
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
        dialogVbox1.getChildren().addAll(contestidHbox);
        if (isEditable)
            dialogVbox1.getChildren().addAll(update);
        else dialogVbox1.getChildren().addAll(add);


        root.getChildren().addAll(dialogVbox1);
        Scene dialogScene = new Scene(root, 600, 850);
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
