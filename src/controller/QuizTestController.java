package controller;

import animatefx.animation.FadeIn;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.stage.StageStyle;
import model.QuizTestGrammarModel;
import model.TestGrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Progressingbar:http://java-buddy.blogspot.com/2014/02/update-javafx-ui-in-scheduled-task-of.html
 * https://github.com/HanSolo/timer
 * Custom Progress bar:https://stackoverflow.com/questions/19417246/how-can-i-style-the-progressbar-component-in-javafx
 * docs oracle:https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/progress.htm
 * stop/start/restart/interrupt thread:https://stackoverflow.com/questions/1881714/how-to-start-stop-restart-a-thread-in-java
 * killing a thread:https://stackoverflow.com/questions/39683952/how-to-stop-a-running-thread-in-java
 * and https://www.baeldung.com/java-thread-stop*/
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
    private String[] clone;
    Thread myRunnableThread;
    MyRunnable myRunnable;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private TestGrammarModel model;
    private Map<String, List<List<String>>> listQuestion;
    List<Map.Entry<String, List<List<String>>>> entryList;
    List<String> key;
    int selectedIdx;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setVisible(false);
        model = new TestGrammarModel();
        min.setText("15");
        listQuestion = model.getTestGrammarFollowGrammarId();
         entryList = new ArrayList<>(listQuestion.entrySet());
         key = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> entry :
                entryList ) {
            Label label = new Label(entry.getKey() );
            key.add(entry.getKey());
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            contestLists.getItems().add(label);
        }
        answer = new ArrayList<>();
        addListener();
        listView.setFocusTraversable( false );
        //set disable
        min.setVisible(false);
        sec.setVisible(false);
        dot.setVisible(false);
        progressBar.setVisible(false);
    }

    private void addListener(){
        contestLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            submit.setVisible(true);
           selectedIdx = contestLists.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIdx);
            progressBar.setVisible(true);
            progressBar.setProgress(0);
            myRunnable  =new MyRunnable(progressBar);
            myRunnableThread = new Thread(myRunnable);
            myRunnableThread.start();
            isRunning.set(true);
            List<List<String>> tmp = entryList.get(selectedIdx).getValue();
            welcomeText.setVisible(false);
            description.setText(key.get(selectedIdx));
            initQuestion();
            min.setVisible(true);
            sec.setVisible(true);
            dot.setVisible(true);
            listView.getItems().clear();
            clone = saveList(answer.get(selectedIdx));
            listView.setCellFactory(param -> {
                QuestionCell cell = new QuestionCell(false);

                    cell.getGroup().selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
                        if (cell.getGroup().getSelectedToggle() != null) {
                            if (cell.getGroup().getSelectedToggle().getUserData() != null )
//
                                //get topic's index is selected
                                if (cell.optionA.isSelected())
                                    answer.get(selectedIdx)[cell.getIndex()]  = "A";
                                else  if (cell.optionB.isSelected())
                                    answer.get(selectedIdx)[cell.getIndex()]  = "B";
                                else  if (cell.optionC.isSelected())
                                    answer.get(selectedIdx)[cell.getIndex()]  = "C";
                                else
                                    answer.get(selectedIdx)[cell.getIndex()]  = "D";
                                listView.getItems().addListener(new ListChangeListener<TestGrammarModel.Question>() {
                                    @Override
                                    public void onChanged(Change<? extends TestGrammarModel.Question> c) {
                                        System.out.println("thanh tai nguyen");
                                    }
                                });
                        //    System.out.println(answer.get(selectedIdx)[cell.getIndex()]);
                        }

                        if (answer.get(selectedIdx)[cell.getIndex()].equals(cell.getItem().getKey())){
                            answer.get(selectedIdx)[answer.get(selectedIdx).length -2] =
                                    Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length -2 ])+1);
                            answer.get(selectedIdx)[answer.get(selectedIdx).length -1] =
                                    Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length-1]) -1) ;
                        }
                    });
//                }else {
//                    //check user's answer
//                    if (answer.get(selectedIdx)[getIndex()] != null){
//                        switch (answer.get(selectedIdx)[getIndex()]){
//                            case "A": optionA.setSelected(true);
//                                optionA.setStyle("-fx-background-color: #FF6A53");
//                                break;
//                            case "B": optionB.setSelected(true);
//                                optionB.setStyle("-fx-background-color: #FF6A53");
//                                break;
//                            case "C": optionC.setSelected(true);
//                                optionC.setStyle("-fx-background-color: #FF6A53");
//                                break;
//                            case "D": optionD.setSelected(true);
//                                optionD.setStyle("-fx-background-color: #FF6A53");
//                                break;
//                        }
//                        System.out.println(answer.get(selectedIdx)[getIndex()]);
//                    }
//                    //show key
//                    switch (item.getKey()){
//                        case "A":
//                            optionA.setStyle("-fx-background-color: #499C54");
//                            break;
//                        case "B":
//                            optionB.setStyle("-fx-background-color: #499C54");
//                            break;
//                        case "C":
//                            optionC.setStyle("-fx-background-color: #499C54");
//                            break;
//                        case "D":
//                            optionD.setStyle("-fx-background-color: #499C54");
//                            break;
//                    }
//                }

              return cell;
            });
            for (int i =0; i< tmp.get(1).size(); i++ ) {
                listView.getItems().add( new TestGrammarModel.Question(key.get(selectedIdx), tmp.get(1).get(i), tmp.get(2).get(i),tmp.get(3).get(i),
                        tmp.get(4).get(i), tmp.get(5).get(i), "", tmp.get(6).get(i), (tmp.get(0).get(i)),  tmp.get(6).get(i)));
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
                        isRunning.set(false);
                    }
                });
        no.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                    dialog.close();
                    submit.setVisible(true);
                    isRunning.set(false);
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
        dialog.initStyle(StageStyle.TRANSPARENT);
        isRunning.set(false);
        review.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, clone);
                    listView.setCellFactory(param -> new QuestionCell(true));
                    // inside here you can use the minimize or close the previous stage//
                    dialog.close();
                    submit.setVisible(true);
                    isRunning.set(false);
                });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, clone);
                    System.out.println(Arrays.toString(answer.get(0)));
                    //refresh listview when user restart contest.
                    listView.setCellFactory(param -> new QuestionCell(false));
                    dialog.close();

                    submit.setVisible(true);
                    isRunning.set(true);
                    if (!myRunnableThread.isAlive()){
                        myRunnableThread = new Thread(myRunnable);
                        myRunnableThread.start();
                    }

                });
        Scene dialogScene = new Scene(dialogVbox1, 600, 450);
//        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);


    }

    public class QuestionCell extends ListCell<TestGrammarModel.Question> {
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

        public ToggleGroup getGroup() {
            return group;
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
            isRunning.set(true);
            while (isRunning.get()){
                for (float i = 1; i <=100; i ++) {
                    final double update_i = i;
                    final int updateSec = (int) (i % 60);
                    //Update JavaFX UI with runLater() in UI thread
                    Platform.runLater(new Runnable(){
                        final int minValue = Integer.valueOf(min.getText());
                        final int s = (60- updateSec) % 60;
                        final int m = s == 0 ? minValue-1: minValue;
                        @Override
                        public void run() {
                            bar.setProgress(update_i / 100);
                            min.setText(String.valueOf(m));
                            sec.setText(String.valueOf(s));

                            if (m == 0 && s == 0)
                                OnSubmit();
                        }
                    });
                    if (!isRunning.get())
                        break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(QuizTestController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }
}
