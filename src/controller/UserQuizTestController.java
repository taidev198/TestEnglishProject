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
import javafx.geometry.Orientation;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.QuizTestModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Progressing bar:http://java-buddy.blogspot.com/2014/02/update-javafx-ui-in-scheduled-task-of.html
 * https://github.com/HanSolo/timer
 * Custom Progress bar:https://stackoverflow.com/questions/19417246/how-can-i-style-the-progressbar-component-in-javafx
 * docs oracle:https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/progress.htm
 * stop/start/restart/interrupt thread:https://stackoverflow.com/questions/1881714/how-to-start-stop-restart-a-thread-in-java
 * killing a thread:https://stackoverflow.com/questions/39683952/how-to-stop-a-running-thread-in-java
 * and https://www.baeldung.com/java-thread-stop*/
public class UserQuizTestController implements Initializable {

    @FXML
    ScrollPane rootLayout;
    @FXML
    ListView<Label> contestLists;
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
    int times = 1;
    String totalTime ;
    Map<Integer, List<RadioButton>> listButton = new HashMap<>();
    private List<String[]> answer;
    private String[] clone;
    Thread myRunnableThread;
    MyRunnable myRunnable;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private QuizTestModel model;
    private TreeMap<String, List<List<String>>> listQuestion;
    List<Map.Entry<String, List<List<String>>>> entryList;
    List<String> key;
    Integer selectedIdx;
    Boolean isSubmited ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setVisible(false);
        submit.setOnAction(event -> {
            if (!isSubmited) {
                submit.setText("RESTART");
                OnSubmit();
               // this.isSubmited = true;
                System.out.println("submit");
            }
            else {
                answer.set(selectedIdx, clone);
                System.out.println(Arrays.toString(answer.get(0)));
                //refresh listview when user restart contest.
                this.isSubmited = false;
                submit.setVisible(true);

                createQuestion(entryList.get(selectedIdx).getValue());
                if (!myRunnableThread.isAlive()){
                    myRunnableThread = new Thread(myRunnable);
                    myRunnableThread.start();
                }
                System.out.println("restart");
                submit.setText("SUBMIT");
            }
        });
        model = new QuizTestModel();
        min.setText("15");
        listQuestion = model.getContest();
         entryList = new ArrayList<>(listQuestion.entrySet());
        System.out.println(entryList.size() + "size");
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
        this.isSubmited = true;
        //set disable
        min.setVisible(false);
        sec.setVisible(false);
        dot.setVisible(false);
        progressBar.setVisible(false);
    }

    private void createQuestion(List<List<String>>  listQuestion) {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        rootLayout.setPadding(new Insets(10, 15, 10, 10));
        flowPane.setPadding(new Insets(10, 15, 10, 10));
        flowPane.setVgap(10);
        flowPane.setHgap(20);
        flowPane.setPrefWrapLength(340);
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            //init question and add event handler on each questions.
            HBox content;
            HBox choose;
            Text question;
            RadioButton optionA;
            RadioButton optionB;
            RadioButton optionC;
            RadioButton optionD;
            Text number;
            VBox vBox;
            ToggleGroup group;
            question = new Text(i+": " +listQuestion.get(1).get(i));
            optionA = new RadioButton("A " + listQuestion.get(2).get(i));
            optionB = new RadioButton("B " + listQuestion.get(3).get(i));
            optionC = new RadioButton("C " + listQuestion.get(4).get(i));
            optionD = new RadioButton("D " + listQuestion.get(5).get(i));
            List<RadioButton> list = new ArrayList<>();
            list.add(optionA);
            list.add(optionB);
            list.add(optionC);
            list.add(optionD);
            listButton.put(i,list);
            number = new Text();
            content = new HBox(number, question);
            choose= new HBox(  optionA, optionB ,
                    optionC ,optionD);
            content.setPadding(new Insets(0,0,0,10));
            optionA.setPrefWidth(250);
            optionB.setPrefWidth(250);
            optionC.setPrefWidth(250);
            optionD.setPrefWidth(250);

            HBox.setMargin(optionA, new Insets(40, 0, 30, 0));
            HBox.setMargin(optionB, new Insets(40, 0, 30, 0));
            HBox.setMargin(optionC, new Insets(40, 0, 30, 0));
            HBox.setMargin(optionD, new Insets(40, 0, 30, 0));
            group = new ToggleGroup();
            optionA.setFont(Font.font(18));
            optionB.setFont(Font.font(18));
            optionC.setFont(Font.font(18));
            optionD.setFont(Font.font(18));
            question.setFont(Font.font(18));
            number.setFont(Font.font(18));
            optionB.setToggleGroup(group);
            optionA.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);

            //set user date
            optionA.setUserData(listQuestion.get(2).get(i));
            optionB.setUserData(listQuestion.get(3).get(i));
            optionC.setUserData(listQuestion.get(4).get(i));
            optionD.setUserData(listQuestion.get(5).get(i));

            choose.setAlignment(Pos.CENTER);
            vBox = new VBox(content, choose);
            vBox.setSpacing(10);
            vBox.setPrefWidth(1100);
            int finalI = i;
            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (group.getSelectedToggle() != null){
                    if (optionA.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "A";
                        System.out.println(finalI + " "+optionA.getUserData());
                    }
                    else if (optionB.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "B";
                        System.out.println(finalI + " "+optionB.getUserData());
                    }
                    else  if (optionC.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "C";
                        System.out.println(finalI + " "+optionC.getUserData());
                    }
                    else {
                        answer.get(selectedIdx)[finalI]  = "D";
                        System.out.println(finalI + " "+optionD.getUserData());
                    }
                }
                if (answer.get(selectedIdx)[finalI].equals(listQuestion.get(6).get(finalI))){
                    answer.get(selectedIdx)[answer.get(selectedIdx).length -2] =
                            Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length -2 ])+1);
                    answer.get(selectedIdx)[answer.get(selectedIdx).length -1] =
                            Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length-1]) -1) ;
                }
                System.out.println(Arrays.toString(answer.get(selectedIdx)));

            });
            vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);" +
                    "-fx-background-color: white;" + "-fx-border-radius: 10;"); // Shadow effect
            //vBox.setEffect(new DropShadow(2d, 0d, +2d, Color.WHITE));
            flowPane.getChildren().add(vBox);
        }

        //flowPane.setPadding(new Insets(5, 0, 5, 0));
        //    flowPane.setEffect(new DropShadow(2d, 0d, +2d, new Color("#D1D7DB")));
        rootLayout.setContent(flowPane);
    }

    private void checkAns(){
        List<List<String>> tmp = entryList.get(selectedIdx).getValue();
       List<Map.Entry<Integer, List<RadioButton>>> entryList = new ArrayList<>(listButton.entrySet());
        for (Map.Entry<Integer, List<RadioButton>> entry:
            entryList ) {
            if (answer.get(selectedIdx)[entry.getKey()] != null) {
                switch (answer.get(selectedIdx)[entry.getKey()]) {
                    case "A":
                        entry.getValue().get(0).setSelected(true);
                        entry.getValue().get(0).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "B":
                        entry.getValue().get(1).setSelected(true);
                        entry.getValue().get(1).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "C":
                        entry.getValue().get(2).setSelected(true);
                        entry.getValue().get(2).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "D":
                        entry.getValue().get(3).setSelected(true);
                        entry.getValue().get(3).setStyle("-fx-background-color: #FF6A53");
                        break;
                }
            }
            if (answer.get(selectedIdx)[entry.getKey()] != null) {
                switch (tmp.get(6).get(entry.getKey())) {
                    case "A":
                        entry.getValue().get(0).setStyle("-fx-background-color: #499C54");
                        break;
                    case "B":

                        entry.getValue().get(1).setStyle("-fx-background-color: #499C54");
                        break;
                    case "C":

                        entry.getValue().get(2).setStyle("-fx-background-color: #499C54");
                        break;
                    case "D":

                        entry.getValue().get(3).setStyle("-fx-background-color: #499C54");
                        break;
                }
            }
        }
    }

    private void addListener(){
        contestLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (this.isSubmited){
                submit.setText("SUBMIT");
                submit.setVisible(true);
                this.isSubmited = false;
                selectedIdx = contestLists.getSelectionModel().getSelectedIndex();
                progressBar.setVisible(true);
                progressBar.setProgress(0);
                myRunnable  =new MyRunnable(progressBar);
                myRunnableThread = new Thread(myRunnable);
                if (myRunnableThread.isAlive())
                    myRunnableThread.stop();
                myRunnableThread.start();
                isRunning.set(true);
                List<List<String>> tmp = entryList.get(selectedIdx).getValue();
                listButton.clear();
                createQuestion(tmp);
                welcomeText.setVisible(false);
                description.setText(key.get(selectedIdx));
                initQuestion();
                min.setVisible(true);
                sec.setVisible(true);
                dot.setVisible(true);
                clone = saveList(answer.get(selectedIdx));
            }else {
                contestLists.requestFocus();
                contestLists.getSelectionModel().select(selectedIdx);
                contestLists.getFocusModel().focus(selectedIdx);
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
        newList[list.length-2] = "0";
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
        dialog.initOwner(description.getScene().getWindow());

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
                    checkAns();
                    this.isSubmited = true;
                    answer.set(selectedIdx, saveList(clone));
                    // inside here you can use the minimize or close the previous stage//
                    dialog.close();
                    submit.setVisible(true);
                    isRunning.set(false);
                });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, saveList(clone));
                    System.out.println(Arrays.toString(answer.get(0)));
                    //refresh listview when user restart contest.
                    dialog.close();
                    this.isSubmited = false;
                    submit.setVisible(true);
                    createQuestion(entryList.get(selectedIdx).getValue());
                    if (!myRunnableThread.isAlive()){
                        myRunnableThread = new Thread(myRunnable);
                        myRunnableThread.start();
                    }

                });


        //adding result to database
        model.addResult(new QuizTestModel.TestResult(Integer.toString(UserController.userId), "1", entryList.get(selectedIdx).getValue().get(0).get(0)
                , Integer.toString(numberOfCorrect), Integer.toString(numberOfWrong),Integer.toString(times++), totalTime));

        Scene dialogScene = new Scene(dialogVbox1, 600, 450);
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);
    }


    class MyRunnable implements Runnable{

        ProgressBar bar;

        MyRunnable(ProgressBar b) {
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
                            bar.setProgress(update_i /100);
                            min.setText(String.valueOf(m));
                            sec.setText(String.valueOf(s));
                            totalTime = String.valueOf(update_i);
                            if (m == 0 && s == 0){
                                OnSubmit();
                                totalTime = "600";
                            }

                        }
                    });
                    if (!isRunning.get())
                        break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(UserQuizTestController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }
}
