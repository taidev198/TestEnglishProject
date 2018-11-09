package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class TestGrammarController implements Initializable {

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
}
