package controller;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    Pane pane;
    @FXML
    Pane pane1;

    @FXML
    AnchorPane anchorPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goHome(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
           anchorPane.getChildren().clear();
           anchorPane.getChildren().addAll(root);
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signupView.fxml"));
            pane.getChildren().clear();
            pane.getChildren().addAll(root);
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void backScene(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/SignInView.fxml"));
            pane1.getChildren().clear();
            pane1.getChildren().addAll(root);
            new FadeIn(root).play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
