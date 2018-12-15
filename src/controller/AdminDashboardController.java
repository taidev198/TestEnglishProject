package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by traig on 8:33 PM, 11/14/2018
 */
public class AdminDashboardController implements Initializable {

    private ObservableList<PieChart.Data> listDataLinechart = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> listDataLinechart1 = FXCollections.observableArrayList();
    @FXML
    PieChart pieChart;
    @FXML
    PieChart pieChart1;
    @FXML
    LineChart<?, ?> lineChart;

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
    }
}
