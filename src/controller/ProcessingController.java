package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by traig on 4:23 PM, 12/7/2018
 */
public class ProcessingController implements Initializable {
    @FXML
    ProgressIndicator progressIndicator;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        progressIndicator.setProgress(-1.0f);
    }
}
