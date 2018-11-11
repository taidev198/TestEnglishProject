package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainContentController implements Initializable {
    @FXML
    private TextArea content;
    @FXML
    private AnchorPane pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //content.setText("thanh tai nguyen");
        this.content.setWrapText(true);
        this.content.setEditable(false);
        this.content.setFocusTraversable(false);
    }

    public void setContent(String title){
       content.setText(title);
    }
}
