package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

        Question q =  new Question("hello", "You can easily increase the size of the Radio Button and Checkbox icons in SurveyGizmo surveys so that they are easier for your survey respondents to select.", "a","b",
                "c", "d", "", "a", 1);
        Question q1 =  new Question("hello", "hello", "b","b",
                "c", "d", "", "a", 1);
        Question q2 =  new Question("hello", "hello", "c","b",
                "c", "d", "", "a", 1);
        Question q3 =  new Question("hello", "hello", "d","b",
                "c", "d", "", "a", 1);
        Question q4 =  new Question("hello", "hello", "e","b",
                "c", "d", "", "a", 1);
        Question q5 =  new Question("hello", "hello", "f","b",
                "c", "d", "", "a", 1);
        Question q6 =  new Question("hello", "hello", "g","b",
                "c", "d", "", "a", 1);
         listView.getItems().addAll(q);

        listView.getItems().addAll(q1);
        listView.getItems().addAll(q2);
        listView.getItems().addAll(q3);
        listView.getItems().addAll(q4);
        listView.getItems().addAll(q5);
        listView.getItems().addAll(q6);
        listView.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
            @Override
            public ListCell<Question> call(ListView<Question> param) {
                return new QuestionCell();
            }
        });
         listView.getItems().addListener(new ListChangeListener<Question>() {
             @Override
             public void onChanged(Change<? extends Question> c) {
                 while (c.next()){
                     System.out.println(c.getFrom());
                 }
             }
         });
        listView.setFocusTraversable( false );


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
        ChangeListener<Boolean> radioListener = (src, ov, nv) -> radioChanged(nv);
        WeakChangeListener<Boolean> weakRadioListener = new WeakChangeListener(radioListener);

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
            final ToggleGroup group = new ToggleGroup();
            optionB.setToggleGroup(group);
            optionA.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);
            optionA.selectedProperty().addListener(weakRadioListener);
            optionB.selectedProperty().addListener(weakRadioListener);
            optionC.selectedProperty().addListener(weakRadioListener);
            optionD.selectedProperty().addListener(weakRadioListener);
            choose.setAlignment(Pos.CENTER);
             vBox = new VBox(content, choose);
            vBox.setSpacing(10);
            if (optionA.isSelected()){
                System.out.println("thanh tai nguyen");
            }
        }

        @Override
        protected void updateItem(Question item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                number.setText(String.valueOf(item.getNumber()) + ": ");
                setGraphic(vBox);
                if (optionA.isSelected()){
                    System.out.println("thanh tai nguyen");
                }

            }else setGraphic(null);
        }

        protected void radioChanged(boolean selected) {
            if (selected && getListView() != null && !isEmpty() && getIndex() >= 0) {
                getListView().getSelectionModel().select(getIndex());
                System.out.println(getListView().getSelectionModel().getSelectedItem().getOptionA());
                int idx = getIndex();
               // System.out.println(idx);
                if (optionA.isSelected()){
                    System.out.println("thanh tai nguyen");
                }else if (optionB.isSelected())
                    System.out.println(optionA.getText());

            }
        }

    }
}
