package controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.TestGrammarModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**How to fix bug at TableColumn<nameClass, Integer> but TableView using String in column
 * https://stackoverflow.com/questions/27468546/how-to-use-simpleintegerproperty-in-javafx</>*/
public class AdminTestGrammarController implements Initializable {

    @FXML
    TableView<TestGrammarModel.QuestionTableView> tableView;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, Integer> idCol;
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

    MenuButton menuButton[];
    TestGrammarModel model;
    private  List<List<String>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new TestGrammarModel();
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
        menuButton = new MenuButton[listQuestion.get(0).size()];
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            menuButton[i] = new MenuButton();
            MenuItem edit = new MenuItem("edit");
            MenuItem delete = new MenuItem("delete");
            int idx = i;
            menuButton[i].setText("...");
            menuButton[i].getItems().addAll(edit);
            menuButton[i].getItems().addAll(delete);
            edit.setOnAction(event -> {
                editableCols();
                System.out.println("edit");
                tableView.getItems().get(idx);
            });
            tableView.getItems().addAll(new TestGrammarModel.QuestionTableView(listQuestion.get(1).get(i),
                    listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                    listQuestion.get(4).get(i), listQuestion.get(5).get(i), "",
                    listQuestion.get(6).get(i), Integer.valueOf(listQuestion.get(0).get(i)), listQuestion.get(7).get(i) , menuButton[i]) );


        }
        tableView.setRowFactory(new Callback<TableView<TestGrammarModel.QuestionTableView>, TableRow<TestGrammarModel.QuestionTableView>>() {
            @Override
            public TableRow<TestGrammarModel.QuestionTableView> call(TableView<TestGrammarModel.QuestionTableView> param) {
                return new rowQuestion();
            }
        });

    }

    private void editableCols(){
        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));
        idCol.setOnEditCommit(event -> event.getTableView().getItems().
                    get(event.getTablePosition().getRow()).setNumber(event.getNewValue()));
        tableView.setEditable(true);
    }


    private void handleEvent(ActionEvent event){
        if (event.getSource().equals(menuButton[0])){
            System.out.println("thanh tai nguyen");
        }
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
    }
}
