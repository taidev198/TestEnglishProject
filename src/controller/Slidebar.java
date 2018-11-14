package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Slidebar implements Initializable {
    @FXML
    BorderPane borderPane ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void slide() throws IOException {
        borderPane = FXMLLoader.load(getClass().getResource("/main/view/AdminView.fxml"));
        System.out.println("thanh tai nguyen");
        Parent root ;
        root = FXMLLoader.load(getClass().getResource("/main/view/grammarView.fxml"));
        borderPane.setCenter(root);
        borderPane.getChildren().add(root);
       // borderPane = new BorderPane(root);

    }

}
