package controller;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestGrammarController implements Initializable {

    @FXML
    AnchorPane anchorPane;
    boolean isFinish = true;

    @FXML
    Pane pane;
    @FXML
    private ListView<Label> listContests;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 14; i++) {
            Label label = new Label("AS, When Or While ,AS, When Or While" );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            listContests.getItems().add(label);

        }

            listContests.setOpaqueInsets(new Insets(10));
            listContests.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    FXMLLoader loader=  new FXMLLoader(getClass().getResource("/view/MainContentView.fxml"));
                    Parent root =  loader.load();
                    MainContentController m = loader.getController();
                    m.setContent("1");System.out.println("thanh tai nguyen");
//                        pane.getChildren().removeAll();
//                        pane.getChildren().addAll(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
       // listContests.getSelectionModel().re;

    }

    @FXML
    private void backHome(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TestGrammar view");
    }


    @FXML
    public void next(){
        isFinish = true;
    }

}
