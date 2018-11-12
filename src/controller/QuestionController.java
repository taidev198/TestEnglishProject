package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.TestGrammarModel;

import java.net.URL;
import java.util.*;

public class QuestionController implements Initializable {

    @FXML
    ListView<Label> grammarLists;
    @FXML
    ListView<TestGrammarModel.Question> listView;
    @FXML
    AnchorPane anchorPane;

    @FXML
    Text description;

    private TestGrammarModel model;
    private Map<String, List<List<String>>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new TestGrammarModel();
        listQuestion = model.getTestGrammar();
        List<Map.Entry<String, List<List<String>>>> entryList = new ArrayList<>(listQuestion.entrySet());
        List<String> key = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> entry :
               entryList ) {
            Label label = new Label(entry.getKey() );
            key.add(entry.getKey());
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            grammarLists.getItems().add(label);
        }

        for (int i = 0; i < 14; i++) {
            Label label = new Label("AS, When Or While ,AS, When Or While" );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            grammarLists.getItems().add(label);
        }
        grammarLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int index = grammarLists.getSelectionModel().getSelectedIndex();
            List<List<String>> tmp = entryList.get(index).getValue();
            description.setText(key.get(index));
            for (int i =0; i< tmp.get(1).size(); i++ ) {
                listView.getItems().addAll( new TestGrammarModel.Question(key.get(index), tmp.get(1).get(i), tmp.get(2).get(i),tmp.get(3).get(i),
                        tmp.get(4).get(i), tmp.get(5).get(i), "", tmp.get(6).get(i), Integer.valueOf(tmp.get(0).get(i))));
            }
        });
        listView.setCellFactory(new Callback<ListView<TestGrammarModel.Question>, ListCell<TestGrammarModel.Question>>() {
            @Override
            public ListCell<TestGrammarModel.Question> call(ListView<TestGrammarModel.Question> param) {
                return new QuestionCell();
            }
        });
         listView.getItems().addListener(new ListChangeListener<TestGrammarModel.Question>() {
             @Override
             public void onChanged(Change<? extends TestGrammarModel.Question> c) {
                 while (c.next()){
                 //    System.out.println(c.getFrom());
                 }
             }
         });
        listView.setFocusTraversable( false );
    }


    private class QuestionCell extends ListCell<TestGrammarModel.Question>{
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
        WeakChangeListener weakRadioListener = new WeakChangeListener(radioListener);

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
        protected void updateItem(TestGrammarModel.Question item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                optionB.setText(item.getOptionB());
                optionC.setText(item.getOptionC());
                optionD.setText(item.getOptionD());
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
                System.out.println(getListView().getSelectionModel().getSelectedItem().getKey());

            }
        }
    }
}
