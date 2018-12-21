package controller;


import animatefx.animation.FadeIn;
import helper.LoadSceneHelper;
import helper.Progressable;
import helper.WorkIndicatorDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GrammarModel;
import model.QuizTestModel;
import model.UserModel;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/***Why am I getting java.lang.IllegalStateException “Not on FX application thread” on JavaFX?:
 * https://stackoverflow.com/questions/17850191/why-am-i-getting-java-lang-illegalstateexception-not-on-fx-application-thread
 * dialog progress:https://www.rational-pi.be/2016/05/modal-jaxafx-progress-indicator-running-in-background/*/
public class UserController implements Initializable, LoadSceneHelper, Progressable {

    private double xOffset = 0;
    private double yOffset = 0;
    private ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> list1 = FXCollections.observableArrayList();
    @FXML
    PieChart pieChart;
    @FXML
    PieChart pieChart1;

    @FXML
    AnchorPane anchorPane;

    @FXML
    AnchorPane anchorPane1;

    @FXML
    LineChart<?, ?> lineChart;
    @FXML
     Label userName;
    static String temp;
    QuizTestModel quizTestModel;
    private List<List<String>> quizTestResult;
    List<String > listContest;
    UserModel userModel;

    GrammarModel grammarModel;
    public static int userId;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragerable();
        grammarModel = new GrammarModel();
        userModel = new UserModel();
        //get userid
        userId = userModel.getUserId(temp);
        System.out.println(userId);
        quizTestModel = new QuizTestModel();


        quizTestResult = quizTestModel.getResult(userId);
        listContest = userModel.getResultUser(userId);
        System.out.println(quizTestResult);
        //pie chart
        list.add(new PieChart.Data("Completed Grammar", grammarModel.getTotalCompletedGrammarByUser(userId)));
        list.add(new PieChart.Data("Uncompleted Grammar",grammarModel.getTotalGrammar() -list.get(0).getPieValue()));
        list1.add(new PieChart.Data("Completed Contest", quizTestModel.getTotalCompletedContestByUser(userId)));
        list1.add(new PieChart.Data("Uncompleted Contest", quizTestModel.getTotalContest()- list1.get(0).getPieValue()));
        pieChart.setData(list);
        pieChart.setStartAngle(90);

        pieChart.setTitle("OverView Grammar");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart1.setData(list1);
        pieChart1.setStartAngle(90);

        pieChart1.setTitle("OverView Contest");
        pieChart1.setLegendSide(Side.BOTTOM);

        //LINE CHART
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < listContest.size(); i++) {
            System.out.println("result:" + quizTestResult.get(2).get(i));
            series.getData().add(new XYChart.Data<>(listContest.get(i), Integer.valueOf(quizTestResult.get(2).get(i))));
        }
        series.setName("User Account");
        lineChart.setTitle("RESULTS OF QUIZ TESTS");
        lineChart.setTitleSide(Side.TOP);

        lineChart.getData().addAll(series);
        userName.setText(temp);
    }

    public static void setUserName(String username){
        temp = username;
    }

    private void makeStageDragerable() {
        anchorPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        anchorPane.setOnMouseDragged(event -> {
            Main.stage.setX(event.getScreenX() - xOffset);
            Main.stage.setY(event.getScreenY() - yOffset);
            Main.stage.setOpacity(0.7f);
        });
        anchorPane.setOnDragDone((e) -> {
            Main.stage.setOpacity(1.0f);
        });
        anchorPane.setOnMouseReleased((e) -> {
            Main.stage.setOpacity(1.0f);
        });

    }
    @FXML
    private void goToGrammar(){
        OnProgress("/view/grammarView.fxml", anchorPane);
    }

    @FXML
    private void goToTestGrammar(){
    OnProgress("/view/UserTestGrammarView.fxml", anchorPane);

    }

    @FXML
    private void goToQuizTest(){

        OnProgress("/view/UserQuizTestView.fxml", anchorPane);
    }

    @Override
    public void switchScene(String url, Object parent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            ((AnchorPane) parent).getChildren().clear();
            ((AnchorPane) parent).getChildren().addAll(root);
            new FadeIn((Parent)parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {

    }

    @FXML
    private void logOut(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            ( anchorPane).getChildren().clear();
            ( anchorPane).getChildren().addAll(root);
            Stage stage = (Stage)(anchorPane).getScene().getWindow();
            stage.setHeight(600);
            stage.setWidth(1000);
            Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
            stage.show(); //show stage because you wouldn't be able to get Height & width of the stage
            stage.setX(d.width/2-(stage.getWidth()/2));
            stage.setY(d.height/2-(stage.getHeight()/2));
            new FadeIn((Parent)anchorPane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnProgress(String url, Object parent) {
        final WorkIndicatorDialog[] wd = {null};
        //dialog.showAndWait();
        wd[0] = new WorkIndicatorDialog(anchorPane.getScene().getWindow(), "Loading Project Files...", Toolkit.getDefaultToolkit().getScreenSize());
        wd[0].addTaskEndNotification(result -> {
            System.out.println(result);
            wd[0] =null; // don't keep the object, cleanup
        });
        anchorPane.getScene().getWindow().setOpacity(0.9);
       wd[0].exec("123", inputParam -> {
            // Thinks to do...
            // NO ACCESS TO UI ELEMENTS!
               System.out.println("Loading data... '123' =->"+inputParam);
               try {
                   Thread.sleep(500);
               }
               catch (InterruptedException e) {
                   e.printStackTrace();
               }
           Platform.runLater(() -> {
               // Update UI here.
              switchScene(url, anchorPane);
               anchorPane.getScene().getWindow().setOpacity(1);
           });
            return 1;
        });
    }
}
