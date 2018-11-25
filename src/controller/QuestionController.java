package controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TestGrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;


/**Clone primitive list:https://stackoverflow.com/questions/8441664/how-do-i-copy-the-contents-of-one-arraylist-into-another/29033799
 * custom dialog:https://stackoverflow.com/questions/40031632/custom-javafx-dialog
 * Focus a table row dynamically:https://stackoverflow.com/questions/20413419/javafx-2-how-to-focus-a-table-row-programmatically/20433947*/
public class QuestionController implements Initializable {

    @FXML
    ListView<Label> grammarLists;
    @FXML
    ListView<TestGrammarModel.Question> listView;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text welcomeText;
    @FXML
    Button submit;
    @FXML
    Text description;
    private List<String[]> answer;
    private String[] clone;

    private TestGrammarModel model;
    private Map<String, List<List<String>>> listQuestion;
    List<Map.Entry<String, List<List<String>>>> entryList;
    List<String> key = new ArrayList<>();
    Boolean isSubmited ;
    Integer selectedIdx ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setVisible(false);
        model = new TestGrammarModel();
        listQuestion = model.getTestGrammarFollowGrammarId();
        entryList = new ArrayList<>(listQuestion.entrySet());
        isSubmited = true;
        for (Map.Entry<String, List<List<String>>> entry :
               entryList ) {
            Label label = new Label(entry.getKey() );
            key.add(entry.getKey());
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            grammarLists.getItems().add(label);
        }
        answer = new ArrayList<>();

        initQuestion();
       addListenerToListView();
        listView.setFocusTraversable( false );

    }

    private void addListenerToListView(){
        grammarLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            welcomeText.setVisible(false);
            selectedIdx = grammarLists.getSelectionModel().getSelectedIndex();
            if (this.isSubmited){
                submit.setVisible(true);

                System.out.println(selectedIdx);
                this.isSubmited = false;
                List<List<String>> tmp = entryList.get(selectedIdx).getValue();
                System.out.println(tmp);
                clone = saveList(answer.get(selectedIdx));
                initQuestion();
                listView.getItems().clear();
                grammarLists.setFocusTraversable( false );
                listView.setCellFactory(param -> new QuestionCell(false));
                for (int i =0; i< tmp.get(1).size(); i++ ) {
                    listView.getItems().add( new TestGrammarModel.Question(key.get(selectedIdx), tmp.get(1).get(i), tmp.get(2).get(i),tmp.get(3).get(i),
                            tmp.get(4).get(i), tmp.get(5).get(i), "", tmp.get(6).get(i), (tmp.get(0).get(i)),  tmp.get(6).get(i)));
                }
            }else {
                grammarLists.requestFocus();
                grammarLists.getSelectionModel().select(selectedIdx);
                grammarLists.getFocusModel().focus(selectedIdx);
            }
        });
    }

    private void initQuestion(){
        for (int i = 0; i < entryList.size(); i++) {
            List<List<String>> tmp = entryList.get(i).getValue();
            answer.add(new String[tmp.get(1).size() +2]);
            answer.get(i)[tmp.get(1).size()+1] = String.valueOf(tmp.get(1).size());
            answer.get(i)[tmp.get(1).size()] = String.valueOf("0");
        }
    }

    private String[] saveList(String[] list){

        String[] newList = new String[list.length];
        System.out.println("list:" + Arrays.toString(list));
            newList[list.length-1] = String.valueOf(list.length-2);
            newList[list.length-2] = String.valueOf("0");

        return newList;
    }

    @FXML
    private void back(){
        final Stage dialog = new Stage();
        dialog.setTitle("Confirmation");
        Button back = new Button("BACK");
        Button no = new Button("NO");

        Label displayLabel = new Label("What do you want to do ?");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner( grammarLists.getScene().getWindow());

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        VBox dialogVbox1 = new VBox(20);
        dialogVbox1.setAlignment(Pos.CENTER_LEFT);

        VBox dialogVbox2 = new VBox(20);
        dialogVbox2.setAlignment(Pos.CENTER_RIGHT);

        dialogHbox.getChildren().add(displayLabel);
        dialogVbox1.getChildren().add(back);
        dialogVbox2.getChildren().add(no);

        back.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        // inside here you can use the minimize or close the previous stage//
                        dialog.close();
                        try {
                            Parent parent = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
                            anchorPane.getChildren().clear();
                            anchorPane.getChildren().addAll(parent);
                            new FadeIn(parent).play();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("Main view");
                    }
                });
        no.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                    dialog.close();
                    submit.setVisible(true);
                });

        dialogHbox.getChildren().addAll(dialogVbox1, dialogVbox2);
        Scene dialogScene = new Scene(dialogHbox, 500, 200);
        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);
    }


    @FXML
    public void OnSubmit(){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button review = new Button("REVIEW");
        Button restart = new Button("RESTART");
        //init piechart
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        PieChart res = new PieChart();
        int numberOfWrong = Integer.valueOf( answer.get(selectedIdx)[answer.get(selectedIdx).length -1]);
        int numberOfCorrect = Integer.valueOf( answer.get(selectedIdx)[answer.get(selectedIdx).length -2]);
        list.addAll(new PieChart.Data("Correct :" +numberOfCorrect +" question", numberOfCorrect),
                new PieChart.Data("Wrong :" + numberOfWrong + " question", numberOfWrong));
        res.setData(list);
        res.setStartAngle(90);

        res.setTitle("OverView");
        res.setLegendSide(Side.TOP);


        Label displayLabel = new Label("What do you want to do ?");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner(listView.getScene().getWindow());

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        VBox dialogVbox1 = new VBox(20);
        dialogVbox1.setAlignment(Pos.CENTER);

        //add children
        dialogVbox1.getChildren().addAll(res);
        dialogVbox1.getChildren().addAll(displayLabel);
        dialogVbox1.getChildren().addAll(dialogHbox);
        dialogHbox.getChildren().add(review);
        dialogHbox.getChildren().add(restart);
        review.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, saveList(clone))  ;
                    listView.setCellFactory(param -> new QuestionCell(true));
                    // inside here you can use the minimize or close the previous stage//
                    dialog.close();
                    submit.setVisible(true);
                    grammarLists.setFocusTraversable( true );
                });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, saveList(clone))  ;
                    //refresh listview when user restart contest.
                    listView.setCellFactory(param -> new QuestionCell(false));
                    dialog.close();
                    submit.setVisible(true);
                    grammarLists.setFocusTraversable( true );
                });
        Scene dialogScene = new Scene(dialogVbox1, 600, 450);
//        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);
        this.isSubmited = true;
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
        private ToggleGroup group;
        private boolean isSummitted;
        public QuestionCell(boolean isSummitted){
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
            group = new ToggleGroup();
            optionB.setToggleGroup(group);
            optionA.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);
            choose.setAlignment(Pos.CENTER);
             vBox = new VBox(content, choose);
            vBox.setSpacing(10);
            this.isSummitted = isSummitted;
        }

        @Override
        protected void updateItem(TestGrammarModel.Question item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty ){
                System.out.println("thanh tai nguyen");
                //set text
                question.setText(item.getQuestion());
                optionA.setText("A:  "+item.getOptionA());
                optionB.setText("B:  "+item.getOptionB());
                optionC.setText("C:  "+item.getOptionC());
                optionD.setText("D:  "+item.getOptionD());
                //set font
                optionA.setFont(Font.font(18));
                optionB.setFont(Font.font(18));
                optionC.setFont(Font.font(18));
                optionD.setFont(Font.font(18));
                question.setFont(Font.font(18));
                number.setFont(Font.font(18));
                //set user date
                optionA.setUserData(item.getOptionA());
                optionB.setUserData(item.getOptionB());
                optionC.setUserData(item.getOptionC());
                optionD.setUserData(item.getOptionD());
                number.setText(String.valueOf(item.getNumber()) + ": ");
                setGraphic(vBox);
                //handling event of group radio button.
                if (!isSummitted){
                    group.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
                        if (group.getSelectedToggle() != null) {
                            if (group.getSelectedToggle().getUserData() != null ){

                                if (optionA.isSelected())
                                    answer.get(selectedIdx)[getIndex()]  = "A";
                                else  if (optionB.isSelected())
                                    answer.get(selectedIdx)[getIndex()]  = "B";
                                else  if (optionC.isSelected())
                                    answer.get(selectedIdx)[getIndex()]  = "C";
                                else
                                    answer.get(selectedIdx)[getIndex()]  = "D";
                                System.out.println(answer.get(selectedIdx)[getIndex()]);
                            }
                               System.out.println(group.getSelectedToggle().getUserData().toString());
                            //get topic's index is selected

                        }
                        if (answer.get(selectedIdx)[getIndex()].equals(item.getKey())){
                            answer.get(selectedIdx)[answer.get(selectedIdx).length -2] =
                                    Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length -2 ])+1);
                            answer.get(selectedIdx)[answer.get(selectedIdx).length -1] =
                                    Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length-1]) -1) ;
                        }
                    });
                }else {

                    //check user's answer
                    if (answer.get(selectedIdx)[getIndex()] != null){
                        switch (answer.get(selectedIdx)[getIndex()]){
                            case "A": optionA.setSelected(true);
                                optionA.setStyle("-fx-background-color: #FF6A53");
                                break;
                            case "B": optionB.setSelected(true);
                                optionB.setStyle("-fx-background-color: #FF6A53");
                                break;
                            case "C": optionC.setSelected(true);
                                optionC.setStyle("-fx-background-color: #FF6A53");
                                break;
                            case "D": optionD.setSelected(true);
                                optionD.setStyle("-fx-background-color: #FF6A53");
                                break;
                        }
                    }
                    //show key
                    switch (item.getKey()){
                        case "A":
                            optionA.setStyle("-fx-background-color: #499C54");
                            break;
                        case "B":
                            optionB.setStyle("-fx-background-color: #499C54");
                            break;
                        case "C":
                            optionC.setStyle("-fx-background-color: #499C54");
                            break;
                        case "D":
                            optionD.setStyle("-fx-background-color: #499C54");
                            break;
                    }
                }
            }else {
                   setGraphic(null);
            }

        }
    }
}
