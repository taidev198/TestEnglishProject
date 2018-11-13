package controller;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminGrammarController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadScene(String view){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(view));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
