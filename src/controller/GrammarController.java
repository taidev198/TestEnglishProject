package controller;

import animatefx.animation.FadeIn;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import model.GrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GrammarController implements Initializable {
    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Label> listView;
    @FXML
    private Text description;
    private GrammarModel model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new GrammarModel();
        List<List<String>> lists = model.getGrammar();
        for (int i = 0; i < lists.get(0).size(); i++) {
            Label label = new Label(lists.get(1).get(i) );
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            listView.getItems().add(label);
        }
        load();
            listView.setOpaqueInsets(new Insets(10));
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    FXMLLoader loader=  new FXMLLoader(getClass().getResource("/view/MainContentView.fxml"));
                    Parent root =  loader.load();
                    MainContentController m = loader.getController();
                    description.setText(lists.get(1).get(listView.getSelectionModel().getSelectedIndex()));
                    m.setContent(lists.get(2).get(listView.getSelectionModel().getSelectedIndex()));
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

    private void addGrammar(){

    }

    @FXML
    private void backHome(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TestGrammar view");
    }
    @FXML
    public void next(MouseEvent event){
    }


}
