package controller;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/grammar.fxml"));
        primaryStage.setResizable(false);
        //    primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getIcons().add(new Image("/resource/avatar.png"));//set icon
        stage = primaryStage;
        new FadeIn(root).play();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
