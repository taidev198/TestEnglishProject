package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    ListView<Question> listView;
    @FXML
    AnchorPane anchorPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Question q =  new Question("hello", "hello", "a","b",
                "c", "d", "", "a", 1);

         listView.getItems().addAll(q);
         listView.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
            @Override
            public ListCell<Question> call(ListView<Question> param) {
                return new QuestionCell();
            }
        });


    }

   private static class Question{
        private String content;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String ans;
        private String key;
        private int number;

        public Question(String content, String question, String optionA, String optionB,
                        String optionC, String optionD, String ans, String key, int number) {
            this.content = content;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.ans = ans;
            this.key = key;
            this.number = number;
        }

       public int getNumber() {
           return number;
       }

       public void setNumber(int number) {
           this.number = number;
       }

       public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getAns() {
            return ans;
        }

        public void setAns(String ans) {
            this.ans = ans;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    private class QuestionCell extends ListCell<Question>{
        private HBox content;
        private HBox choose;
        private Text question;
        private RadioButton optionA;
        private RadioButton optionB;
        private RadioButton optionC;
        private RadioButton optionD;
        private Text ans;
        private Text key;
        private Text number;
        private VBox vBox;

        public QuestionCell(){
            super();

            question = new Text();
            optionA = new RadioButton();
            optionB = new RadioButton();
            optionC = new RadioButton();
            optionD = new RadioButton();
            ans = new Text();
            key = new Text();
            number = new Text();
            content = new HBox(number, question);
            choose= new HBox(  optionA, optionB ,
                    optionC ,optionD);
            HBox.setMargin(optionA, new Insets(60, 70, 30, 70));
            HBox.setMargin(optionB, new Insets(60, 70, 30, 70));
            HBox.setMargin(optionC, new Insets(60, 70, 30, 70));
            HBox.setMargin(optionD, new Insets(60, 70, 30, 70));

            choose.setAlignment(Pos.CENTER);
             vBox = new VBox(content, choose);
            vBox.setSpacing(10);

        }

        @Override
        protected void updateItem(Question item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                setGraphic(vBox);


            }else setGraphic(null);
        }
    }
}
