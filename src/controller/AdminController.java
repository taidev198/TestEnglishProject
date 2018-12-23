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
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AdminModel;
import model.GrammarModel;
import model.QuizTestModel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
/**Set center scene:https://stackoverflow.com/questions/29350181/how-to-center-a-window-properly-in-java-fx?noredirect=1&lq=1
 * set size of bars in barchart:https://stackoverflow.com/questions/28047818/limit-width-size-of-bar-chart?rq=1*/
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
    BarChart<String, Integer> barChart;
    private ObservableList<String> contest = FXCollections.observableArrayList();
    @FXML
    Label userName;
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


        userName.setText("ADMIN");
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
