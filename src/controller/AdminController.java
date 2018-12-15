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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**Set center scene:https://stackoverflow.com/questions/29350181/how-to-center-a-window-properly-in-java-fx?noredirect=1&lq=1*/
public class AdminController implements Initializable, LoadSceneHelper, Progressable {
    private static String temp;
    @FXML
    AnchorPane anchorPane1;
    private ObservableList<PieChart.Data> listDataLinechart = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> listDataLinechart1 = FXCollections.observableArrayList();
    @FXML
    AnchorPane anchorPane;
    @FXML
    PieChart pieChart;
    @FXML
    PieChart pieChart1;
    @FXML
    LineChart<?, ?> lineChart;

    @FXML
    Label userName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listDataLinechart.addAll(new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10));
        listDataLinechart1.addAll(new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10),
                new PieChart.Data("tai", 10));
        pieChart.setData(listDataLinechart);
        pieChart.setStartAngle(90);

        pieChart.setTitle("OverView");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart1.setData(listDataLinechart1);
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
        userName.setText("ADMIN");
    }

    @FXML
    private void goToUser(){
       OnProgress("/view/AdminUserView.fxml", anchorPane1);
    }

    @FXML
    private void goToGrammar(){
       OnProgress("/view/AdminGrammarView.fxml", anchorPane1);
    }

    @FXML
    private void goToTestGrammar(){
        OnProgress("/view/AdminTestGrammarView.fxml", anchorPane1);
    }

    @FXML
    private void goToQuizTest(){
        OnProgress("/view/AdminQuestionView.fxml", anchorPane1);
    }

    @FXML
    private void goToOverView(){
        OnProgress("/view/AdminDashboardView.fxml", anchorPane1);
    }

    /**using this interface to load scene*/

    @Override
    public void switchScene(String url, Object parent) {
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((AnchorPane) parent).getChildren().clear();
        ((AnchorPane) parent).getChildren().addAll(user);
    }

    public static void setUserName(String username){
        temp = username;
        System.out.println("thanh tai nguyen");
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
            stage.setX(d.width/2-(stage.getWidth()/2));
            stage.setY(d.height/2-(stage.getHeight()/2));
            stage.show(); //show stage because you wouldn't be able to get Height & width of the stage
            new FadeIn(anchorPane).play();
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
                switchScene(url, anchorPane1);
                anchorPane.getScene().getWindow().setOpacity(1);
            });
            return 1;
        });
    }
}
