package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class contentController implements Initializable {
    @FXML
    private Text text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        text.setText("Nov 03, 2018 8:36:31 AM javafx.view.FXMLLoader$ValueElement processValue\n" +
                "WARNING: Loading FXML document with JavaFX API of version 10.0.1 by JavaFX runtime of version 8.0.181\n" +
                "controller.Main@54a162b\n" +
                "Nov 03, 2018 8:36:34 AM javafx.view.FXMLLoader$ValueElement processValue\n" +
                "WARNING: Loading FXML document with JavaFX API of version 10.0.1 by JavaFX runtime of version 8.0.181\n" +
                "stop system \n" +
                "\n" +
                "Process finished with exit code 0");
    }

    public void setContent(){

    }
}
