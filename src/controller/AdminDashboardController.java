package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import model.AdminModel;
import model.GrammarModel;
import model.QuizTestModel;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by traig on 8:33 PM, 11/14/2018
 */
public class AdminDashboardController implements Initializable {

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
    BarChart<String, Integer> barChart;
    private ObservableList<String> contest = FXCollections.observableArrayList();
    @FXML
    private CategoryAxis xAxis;
    GrammarModel grammarModel;
    QuizTestModel quizTestModel;
    AdminModel model ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new AdminModel();
        grammarModel = new GrammarModel();
        quizTestModel = new QuizTestModel();
        Map<String, int[]> res = model.getResultOfContests();
        System.out.println(res);
        listDataLinechart.add(new PieChart.Data(" completed", grammarModel.getTotalCompletedGrammarByAdmin()));
        listDataLinechart.add( new PieChart.Data("uncompleted ", listDataLinechart.get(0).getPieValue()));
        listDataLinechart1.add(new PieChart.Data("completed ", quizTestModel.getTotalCompletedContestByAdmin()));
        listDataLinechart1.add(new PieChart.Data("uncompleted ", listDataLinechart1.get(0).getPieValue()));
        pieChart.setData(listDataLinechart);
        pieChart.setStartAngle(90);

        pieChart.setTitle("GRAMMAR");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart1.setData(listDataLinechart1);
        pieChart1.setStartAngle(90);

        pieChart1.setTitle("CONTEST");
        pieChart1.setLegendSide(Side.BOTTOM);
        contest.addAll(Arrays.asList("tai0", "tai1", "tai2", "tai3", "tai4"));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(contest);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        // Create a XYChart.Data object for each month. Add it to the series.
        series.setName("HIGH SCORE");
        for (int i = 0; i < 5; i++) {
            series.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series.getData().add(new XYChart.Data<>("tai" +i, 10-i));

        }

        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        // Create a XYChart.Data object for each month. Add it to the series.
        series1.setName("AVER SCORE");
        for (int i = 0; i < 5; i++) {
            series1.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series1.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series1.getData().add(new XYChart.Data<>("tai" +i, 10-i));

        }

        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        // Create a XYChart.Data object for each month. Add it to the series.
        series2.setName("LOW SCORE");
        for (int i = 0; i < 5; i++) {
            series2.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series2.getData().add(new XYChart.Data<>("tai" +i, 10-i));
            series2.getData().add(new XYChart.Data<>("tai" +i, 10-i));

        }
        setMaxBarWidth(30, 10);
        barChart.widthProperty().addListener((obs,b,b1)->{
            Platform.runLater(()->setMaxBarWidth(30, 10));
        });
        barChart.setTitle("BOARD");
        barChart.getData().add(series);
        barChart.getData().add(series1);
        barChart.getData().add(series2);
    }

    private void setMaxBarWidth(double maxBarWidth, double minCategoryGap){
        double barWidth=0;
        do{
            double catSpace = xAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (barChart.getCategoryGap() + barChart.getBarGap());
            barWidth = (avilableBarSpace / barChart.getData().size()) - barChart.getBarGap();
            if (barWidth >maxBarWidth){
                avilableBarSpace=(maxBarWidth + barChart.getBarGap())* barChart.getData().size();
                barChart.setCategoryGap(catSpace-avilableBarSpace-barChart.getBarGap());
            }
        } while(barWidth>maxBarWidth);

        do{
            double catSpace = xAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (minCategoryGap + barChart.getBarGap());
            barWidth = Math.min(maxBarWidth, (avilableBarSpace / barChart.getData().size()) - barChart.getBarGap());
            avilableBarSpace=(barWidth + barChart.getBarGap())* barChart.getData().size();
            barChart.setCategoryGap(catSpace-avilableBarSpace-barChart.getBarGap());
        } while(barWidth < maxBarWidth && barChart.getCategoryGap()>minCategoryGap);
    }
}
