package controller;

import animatefx.animation.FadeIn;
import helper.IResult;
import helper.LoadSceneHelper;
import helper.Progressable;
import helper.WorkIndicatorDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable, LoadSceneHelper, IResult, Progressable {
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
    List<List<String>> listUsers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    model = new UserModel();
        listUsers = model.getUserInfo();
    }

    public void goHome(){
        if (username.getText().equals("admin"))
            OnProgress("/view/AdminView.fxml", anchorPane);
      else   OnProgress("/view/UserView.fxml", anchorPane);
        if (username.getText().equals("") && password.getText().equals(""))
        OnMessage("ERROR: USERNAME AND PASSWORD IS EMPTY");
    else if (username.getText().equals(""))
        OnMessage("ERROR: USERNAME IS EMPTY");
    else if (password.getText().equals(""))OnMessage("ERROR: PASSWORD IS EMPTY");
       else {
            if (isValidUser(username.getText(), password.getText())){
                if (username.getText().equals("admin"))
                    switchScene("/view/AdminView.fxml", anchorPane);
                else switchScene("/view/UserView.fxml", anchorPane);
                anchorPane.getScene().getWindow().setOpacity(1);
            }
            else
                PopUp();
        }

    }


    public boolean isValidUser(String username, String password){

        boolean validUsername = false;
        boolean validPassword = false;
        UserController.setUserName(username);
            if (listUsers.get(1).contains(username))
                validUsername = true;
            if (listUsers.get(2).contains(password))
                validPassword = true;
        if (!validUsername && !validPassword)
            OnMessage("USERNAME AND PASSWORD ARE INCORRECT");
        else if (!validPassword)
            OnMessage("PASSWORD IS INCORRECT");
        else if (!validUsername)
            OnMessage("USERNAME IS INCORRECT");
        return validPassword && validUsername;
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
    @FXML
    public void signUp(){
        switchScene("/view/SignupView.fxml", anchorPane);
        Stage stage = (Stage)(anchorPane).getScene().getWindow();
        stage.setHeight(611);
        stage.setWidth(500);
        Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
        stage.show(); //show stage because you wouldn't be able to get Height & width of the stage
        stage.setX(d.width/2-(stage.getWidth()/2));
        stage.setY(d.height/2-(stage.getHeight()/2));
    }
    public void OnSignUp(){
         if (usernameSignUp.getText().equals(""))
            OnMessage("ERROR: USERNAME IS EMPTY");
        else if (passwordSignUp.getText().equals(""))
            OnMessage("ERROR: PASSWORD IS EMPTY");
        else if (emailSignUp.getText().equals(""))
             OnMessage("ERROR: EMAIL IS EMPTY");
         else if (phoneSignUp.getText().equals(""))
             OnMessage("ERROR: PHONE IS EMPTY");
         else if (!passwordSignUp.getText().equals(confirmSignUp.getText()))
             OnMessage("ERROR: PASSWORD AND CONFIRM PASSWORD IS NOT MATCHED");
        else {
            String email  = emailSignUp.getText();
            if (!phoneSignUp.getText().matches("[0-9]"))
                OnMessage("PHONE NUMBER IS INCORRECT");
            else if (!passwordSignUp.getText().matches("[a-zA-Z0-9._]*"))
                OnMessage("PASSWORD IS INCORRECT");
            else if (!email.contains("@"))
                OnMessage("EMAIL IS INCORRECT");
            else if (!email.substring(email.indexOf("@")+1).matches("[a-z.]*"))
                OnMessage("EMAIL IS INCORRECT");
            else   if (listUsers.get(1).contains(usernameSignUp.getText()))
                OnMessage("USERNAME WAS EXISTED");
            else {
                model.addUser(new UserModel.User(usernameSignUp.getText(), passwordSignUp.getText(), emailSignUp.getText(), phoneSignUp.getText()),true);
                if (usernameSignUp.getText().equals("admin"))
                    OnProgress("/viewAdminView.fxml", anchorPaneSignUp);
                else
                OnProgress("/view/UserView.fxml", anchorPaneSignUp);
                UserController.setUserName(usernameSignUp.getText());
            }

        }
    }

    @Override
    public void switchScene(String url, Object parent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            ((AnchorPane) parent).getChildren().clear();
            ((AnchorPane) parent).getChildren().addAll(root);
            Stage stage = (Stage)((AnchorPane)parent).getScene().getWindow();
            stage.setHeight(850);
            stage.setWidth(1520);
            Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
            stage.show(); //show stage because you wouldn't be able to get Height & width of the stage
            stage.setX(d.width/2-(stage.getWidth()/2));
            stage.setY(d.height/2-(stage.getHeight()/2));
            new FadeIn((Parent)parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {

    }


    @Override
    public void OnMessage(String message) {
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button ok = new Button("OK");

        Label mess = new Label(message);
        AnchorPane root = new AnchorPane();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(anchorPane.getScene().getWindow());

        ok.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
            dialog.close();

                });
        HBox hBox = new HBox(10);
        hBox.getChildren().add(ok);
        hBox.setPadding(new Insets(0,0,0,120));

        VBox vBox = new VBox(80);
        vBox.setPadding(new Insets(30,0,0,100));
        vBox.getChildren().addAll(mess, hBox);
        root.getChildren().addAll(vBox);
        Scene dialogScene = new Scene(root, 400, 200);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    @Override
    public void OnProgress(String url, Object parent) {
        final WorkIndicatorDialog[] wd = {null};
        //dialog.showAndWait();
        wd[0] = new WorkIndicatorDialog(((AnchorPane)parent).getScene().getWindow(), "Loading...", Toolkit.getDefaultToolkit().getScreenSize());
        wd[0].addTaskEndNotification(result -> {
            System.out.println(result);
            wd[0] =null; // don't keep the object, cleanup
        });
        ((AnchorPane)parent).getScene().getWindow().setOpacity(0.9);
        wd[0].exec("123", inputParam -> {
            // Thinks to do...
            // NO ACCESS TO UI ELEMENTS!
            System.out.println("Loading data... '123' =->"+inputParam);
            try {

                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                // Update UI here.
                switchScene(url, parent);
                ((AnchorPane)parent).getScene().getWindow().setOpacity(1);

            });
            return 1;
        });
    }
}
