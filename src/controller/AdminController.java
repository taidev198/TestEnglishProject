package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void user(){
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().clear();
        anchorPane.getChildren().addAll(user);
    }

    @FXML
    private void grammar(){
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource("/view/AdminGrammar.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().clear();
        anchorPane.getChildren().addAll(user);
    }

    @FXML
    private void testGrammar(){
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource("/view/AdminTestGrammar.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().clear();
        anchorPane.getChildren().addAll(user);
    }

    @FXML
    private void quizTest(){
        Parent user = null;
        try {
            user = FXMLLoader.load(getClass().getResource("/view/AdminQuizzTest.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().clear();
        anchorPane.getChildren().addAll(user);
    }
}
