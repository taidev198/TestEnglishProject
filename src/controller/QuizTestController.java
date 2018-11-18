package controller;

import animatefx.animation.FadeIn;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TestGrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Progressingbar:http://java-buddy.blogspot.com/2014/02/update-javafx-ui-in-scheduled-task-of.html
 * https://github.com/HanSolo/timer */
public class QuizTestController implements Initializable {

    @FXML
    ListView<Label> contestLists;
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
    @FXML
    ProgressBar progressBar;
    @FXML
    Text min;
    @FXML
    Text sec;
    @FXML
    Text dot;
    private List<String[]> answer;
    private List<String[]> clone;
    Thread myRunnableThread;

    private TestGrammarModel model;
    private Map<String, List<List<String>>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setVisible(false);
        model = new TestGrammarModel();
        listQuestion = model.getTestGrammarFollowGrammarId();
        List<Map.Entry<String, List<List<String>>>> entryList = new ArrayList<>(listQuestion.entrySet());
        List<String> key = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> entry :
                entryList ) {
            Label label = new Label(entry.getKey() );
            key.add(entry.getKey());
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            contestLists.getItems().add(label);
        }
        answer = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            Label label = new Label("AS, When Or While ,AS, When Or While" );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            contestLists.getItems().add(label);
        }
        contestLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            submit.setVisible(true);
            int index = contestLists.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            progressBar.setVisible(true);
            progressBar.setProgress(0);
            MyRunnable myRunnable  =new MyRunnable(progressBar);
            myRunnableThread = new Thread(myRunnable);
            myRunnableThread.start();
            List<List<String>> tmp = entryList.get(index).getValue();
            welcomeText.setVisible(false);
            description.setText(key.get(index));
            answer.add(new String[tmp.get(1).size() +2]);
            answer.get(index)[tmp.get(1).size()+1] = String.valueOf(tmp.get(1).size());
            answer.get(index)[tmp.get(1).size()] = String.valueOf("0");
            min.setVisible(true);
            sec.setVisible(true);
            dot.setVisible(true);
            clone = saveList(answer);
            for (int i =0; i< tmp.get(1).size(); i++ ) {

                listView.getItems().addAll( new TestGrammarModel.Question(key.get(index), tmp.get(1).get(i), tmp.get(2).get(i),tmp.get(3).get(i),
                        tmp.get(4).get(i), tmp.get(5).get(i), "", tmp.get(6).get(i), (tmp.get(0).get(i)),  tmp.get(6).get(i)));
            }
        });
        listView.setCellFactory(param -> new QuestionCell(false));
        listView.setFocusTraversable( false );
        //set disable
        min.setVisible(false);
        sec.setVisible(false);
        dot.setVisible(false);
        progressBar.setVisible(false);
    }

    private List<String[]> saveList(List<String[]> list){

        List<String[]> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(new  String[list.get(i).length]);
            newList.get(contestLists.getSelectionModel().getSelectedIndex())[list.get(i).length-1] = String.valueOf(list.get(i).length-2);
            newList.get(contestLists.getSelectionModel().getSelectedIndex())[list.get(i).length-2] = String.valueOf("0");
        }
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
        dialog.initOwner( contestLists.getScene().getWindow());

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
        int numberOfWrong = Integer.valueOf( answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length -1]);
        int numberOfCorrect = Integer.valueOf( answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length -2]);
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
                    answer = saveList(clone);
                    listView.setCellFactory(param -> new QuestionCell(true));
                    // inside here you can use the minimize or close the previous stage//
                    dialog.close();
                    submit.setVisible(true);
                });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer = saveList(clone);
                    System.out.println(Arrays.toString(answer.get(0)));
                    //refresh listview when user restart contest.
                    listView.setCellFactory(param -> new QuestionCell(false));
                    dialog.close();
                    submit.setVisible(true);
                });
        Scene dialogScene = new Scene(dialogVbox1, 600, 450);
//        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);

    }

    private class QuestionCell extends ListCell<TestGrammarModel.Question> {
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
                            if (group.getSelectedToggle().getUserData() != null )
//                                System.out.println(group.getSelectedToggle().getUserData().toString());
                                //get topic's index is selected
                                if (optionA.isSelected())
                                    answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]  = "A";
                                else  if (optionB.isSelected())
                                    answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]  = "B";
                                else  if (optionC.isSelected())
                                    answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]  = "C";
                                else
                                    answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]  = "D";
                            System.out.println(answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]);
                        }

                        if (answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()].equals(item.getKey())){
                            answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length -2] =
                                    Integer.toString(Integer.parseInt(answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length -2 ])+1);
                            answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length -1] =
                                    Integer.toString(Integer.parseInt(answer.get(contestLists.getSelectionModel().getSelectedIndex())[answer.get(contestLists.getSelectionModel().getSelectedIndex()).length-1]) -1) ;
                        }
//                        listView.getSelectionModel().select(getIndex());
//                        listView.getFocusModel().focus(listView.getSelectionModel().getSelectedIndex());
                    });
                }else {

                    //check user's answer
                    if (answer.get(contestLists.getSelectionModel().getSelectedIndex())[Integer.parseInt(item.getNumber())-1] != null){
                        switch (answer.get(contestLists.getSelectionModel().getSelectedIndex())[Integer.parseInt(item.getNumber())-1]){
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
                        System.out.println(answer.get(contestLists.getSelectionModel().getSelectedIndex())[getIndex()]);
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

    class MyRunnable implements Runnable{

        ProgressBar bar;

        public MyRunnable(ProgressBar b) {
            bar = b;
        }

        @Override
        public void run() {
            for (int i = 100; i >= 0; i--) {


                final double update_i = i;
                //Update JavaFX UI with runLater() in UI thread
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        bar.setProgress(update_i%100);
                        System.out.println(update_i);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(QuizTestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
