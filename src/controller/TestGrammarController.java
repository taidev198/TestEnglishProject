package controller;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestGrammarController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    private ListView<Label> listContests;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 14; i++) {
            Label label = new Label("AS, When Or While ,AS, When Or While" );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            listContests.getItems().add(label);
        }
    }

    @FXML
    private void backHome(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TestGrammar view");
    }

}
