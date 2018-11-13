package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.TestGrammarModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            tableView.getItems().addAll(new TestGrammarModel.QuestionTableView(listQuestion.get(1).get(i),
                    listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                    listQuestion.get(4).get(i), listQuestion.get(5).get(i), "",
                    listQuestion.get(6).get(i), Integer.valueOf(listQuestion.get(0).get(i)), listQuestion.get(7).get(i) ) );

        }
        tableView.setRowFactory(new Callback<TableView<TestGrammarModel.QuestionTableView>, TableRow<TestGrammarModel.QuestionTableView>>() {
            @Override
            public TableRow<TestGrammarModel.QuestionTableView> call(TableView<TestGrammarModel.QuestionTableView> param) {
                return new rowQuestion();
            }
        });
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
