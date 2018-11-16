package controller;

import helper.LoadSceneAble;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by traig on 9:59 AM, 11/15/2018
 */
public class AdminUserController implements Initializable, LoadSceneAble {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @Override
    public void loadScene(String url, Object parent) {
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

    public void addRow(){

    }
}
