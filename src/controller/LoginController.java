package controller;

import animatefx.animation.FadeIn;
import helper.LoadSceneHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, LoadSceneHelper {
    @FXML
    Pane pane;
    @FXML
    AnchorPane anchorPaneSignUp;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label caution;
    @FXML
    TextField usernameSignUp;
    @FXML
    TextField passwordSignUp;
    @FXML
    TextField confirmSignUp;
    @FXML
    TextField emailSignUp;
    @FXML
    TextField phoneSignUp;
    @FXML
    Label errorLabel;
    UserModel model;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    model = new UserModel();
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
                PopUp();
        }

    }

    public void PopUp(){
        caution.setText("");
        final Stage dialog = new Stage();
        dialog.setTitle("ERROR");
        Button ok = new Button("OK");
        Label displayLabel = new Label("USERNAME OR PASSWORD IS INCORRECT." +
                " \nPLEASE CHECK CAREFULLY!");
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
        switchScene("/view/SignupView.fxml", anchorPane);
    }
    public void OnSignUp(){
         if (usernameSignUp.getText().equals(""))
            errorLabel.setText("ERROR: USERNAME IS EMPTY");
        else if (passwordSignUp.getText().equals(""))
            errorLabel.setText("ERROR: PASSWORD IS EMPTY");
        else if (emailSignUp.getText().equals(""))
             errorLabel.setText("ERROR: EMAIL IS EMPTY");
         else if (phoneSignUp.getText().equals(""))
             errorLabel.setText("ERROR: PHONE IS EMPTY");
         else if (!passwordSignUp.getText().equals(confirmSignUp.getText()))
             errorLabel.setText("ERROR: PASSWORD AND CONFIRM PASSWORD IS NOT MATCHED");
        else {
            model.addUser(new UserModel.User(usernameSignUp.getText(), passwordSignUp.getText(), emailSignUp.getText(), phoneSignUp.getText()),true);
                switchScene("/view/UserView.fxml", anchorPaneSignUp);
        }
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
