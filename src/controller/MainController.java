package controller;


import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
    LineChart<?, ?> lineChart;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDrageable();
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

    private void makeStageDrageable() {
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
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/grammar.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Grammar View");
    }

    @FXML
    private void goToTestGrammar(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/TestGrammar1.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TestGrammar view");

    }
}
