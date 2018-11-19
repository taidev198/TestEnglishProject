package controller;

import helper.LoadSceneHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable, LoadSceneHelper {
    @FXML
    AnchorPane anchorPane1;

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void user(){
       switchScene("/view/AdminUserView.fxml", anchorPane1);
    }

    @FXML
    private void grammar(){
       switchScene("/view/AdminGrammarView.fxml", anchorPane1);
    }

    @FXML
    private void testGrammar(){
        switchScene("/view/AdminTestGrammarView.fxml", anchorPane1);
    }

    @FXML
    private void quizTest(){
        switchScene("/view/AdminQuizzTestView.fxml", anchorPane1);
    }

    @FXML
    private void overView(){
        switchScene("/view/AdminDashboardView.fxml", anchorPane1);
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

    @Override
    public void loadData() {

    }
}
