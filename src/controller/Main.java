package controller;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

/**creating exe file :https://www.youtube.com/watch?v=iqwSjKYqsH0*/
public class Main extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
        primaryStage.setResizable(false);
        //    primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login");
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
//        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

        primaryStage.setScene(new Scene(root, 1520, 800));
        Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
        primaryStage.show(); //show stage because you wouldn't be able to get Height & width of the stage
        primaryStage.setX(d.width/2-(primaryStage.getWidth()/2));
        primaryStage.setY(d.height/2-(primaryStage.getHeight()/2));
        primaryStage.getIcons().add(new Image("/resource/avatar.png"));//set icon
        stage = primaryStage;
        primaryStage.show();
        new FadeIn(root).play();

       // ConnectDataHelper.getInstance().connectDB();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
