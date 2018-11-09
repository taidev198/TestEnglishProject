package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GrammarController implements Initializable {
    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Label> listView;
    @FXML
    private JFXPopup popup;

    private MainContentController mainContentController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int i = 0; i < 14; i++) {
            Label label = new Label("AS, When Or While ,AS, When Or While" );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            listView.getItems().add(label);
        }
        load();
        listView.setOpaqueInsets(new Insets(10));
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                FXMLLoader loader=  new FXMLLoader(getClass().getResource("/view/MainContent.fxml"));
                Parent root =  loader.load();
                MainContentController m = loader.getController();
                m.setContent("1");
                content.getChildren().removeAll();
                content.getChildren().addAll(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void load(){

        System.out.println("thanh tai nguyen");
    }

    @FXML
    public void handle(MouseEvent event){

    }

    public void initPopup(){
        JFXButton btn1 = new JFXButton("task1");
        JFXButton btn2 = new JFXButton("task2");
        JFXButton btn3 = new JFXButton("task3");

        btn1.setPadding(new Insets(10));
        btn2.setPadding(new Insets(10));
        btn3.setPadding(new Insets(10));

        VBox vBox = new VBox(btn1, btn2, btn3);
        popup.setContent(vBox);
        popup.setSource(listView);

    }

    @FXML
    public void showPopup(MouseEvent event){
        popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
    }

}
