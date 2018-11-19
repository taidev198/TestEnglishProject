package controller;

import animatefx.animation.FadeIn;
import helper.LoadSceneHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class loginController implements Initializable, LoadSceneHelper {
    @FXML
    Pane pane;
    @FXML
    Pane pane1;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label caution;
    UserModel model;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    model = new UserModel();
    username.setPromptText("enter your username");
    password.setPromptText("enter your password");
    }

    public void goHome(){
        if (username.getText().equals("") && password.getText().equals(""))
        caution.setText("ERROR: USERNAME AND PASSWORD IS EMPTY");
    else if (username.getText().equals(""))
        caution.setText("ERROR: USERNAME IS EMPTY");
    else caution.setText("ERROR: PASSWORD IS EMPTY");
        if (!username.getText().equals("") && !password.getText().equals("")){
            if (model.isValidUser(username.getText(), password.getText()))
                switchScene("/view/UserView.fxml", anchorPane);
            else
                OnInformation();
        }

    }

    public void OnInformation(){
        caution.setText("");
        final Stage dialog = new Stage();
        dialog.setTitle("ERROR");
        Button ok = new Button("OK");
        Label displayLabel = new Label("USERNAME OR PASSWORD IS INCORRECT." +
                " \nNPLEASE CHECK CAREFULLY!");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner(anchorPane.getScene().getWindow());

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        VBox dialogVbox1 = new VBox(20);
        dialogVbox1.setAlignment(Pos.CENTER);

        //add children

        dialogVbox1.getChildren().addAll(displayLabel);
        dialogVbox1.getChildren().addAll(dialogHbox);
        dialogHbox.getChildren().add(ok);


        ok.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> dialog.close());
        Scene dialogScene = new Scene(dialogVbox1, 400, 100);
//        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();


    }

    public void signUp(){
        switchScene("/view/signupView.fxml", anchorPane);
    }
    public void backScene(){
        switchScene("/view/SignInView.fxml", anchorPane);
    }

    @Override
    public void switchScene(String url, Object parent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            ((AnchorPane) parent).getChildren().clear();
            ((AnchorPane) parent).getChildren().addAll(root);
            new FadeIn((Parent)parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {

    }
}
