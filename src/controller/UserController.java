package controller;


import animatefx.animation.FadeIn;
import helper.LoadSceneHelper;
import helper.Progressable;
import helper.WorkIndicatorDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
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
    Task worker;
    Stage dialog;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragerable();
        list.addAll(new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10));
        list1.addAll(new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10));
        pieChart.setData(list);
        pieChart.setStartAngle(90);

        pieChart.setTitle("OverView");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart1.setData(list1);
        pieChart1.setStartAngle(90);

        pieChart1.setTitle("OverView");
        pieChart1.setLegendSide(Side.BOTTOM);

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("1", 2));
        series.getData().add(new XYChart.Data<>("2", 13));
        series.getData().add(new XYChart.Data<>("3", 21));
        series.getData().add(new XYChart.Data<>("4", 20));
        series.getData().add(new XYChart.Data<>("5", 12));
        series.getData().add(new XYChart.Data<>("6", 25));
        series.setName("User Account");
        lineChart.setTitle("Overview");
        lineChart.setTitleSide(Side.TOP);

        lineChart.getData().addAll(series);

    }

    private void makeStageDragerable() {
        anchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.stage.setX(event.getScreenX() - xOffset);
                Main.stage.setY(event.getScreenY() - yOffset);
                Main.stage.setOpacity(0.7f);
            }
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

        OnProgress("/view/QuizTestView.fxml", anchorPane);
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

    @Override
    public void OnProgress(String url, Object parent) {
        final WorkIndicatorDialog[] wd = {null};
        //dialog.showAndWait();
        wd[0] = new WorkIndicatorDialog(anchorPane.getScene().getWindow(), "Loading Project Files...");
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
