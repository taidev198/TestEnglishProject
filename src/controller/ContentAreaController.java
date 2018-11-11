package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentAreaController implements Initializable {
    
    @FXML
    private VBox content_area;
    @FXML
    private HBox menubar;

    /**
     * Initializes the controller class.
     */
    boolean flag = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void open_sidebar(ActionEvent event) throws IOException {
        BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        if (flag) {
            Parent sidebar = FXMLLoader.load(getClass().getResource("/main/view/SideBar1View.fxml"));
            border_pane.setLeft(sidebar);
            flag = !flag;
        } else {
            border_pane.setLeft(null);
            flag = !flag;
        }
        
    }
    
}
