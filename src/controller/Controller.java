package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller  implements Initializable {
    public HBox dashboard;
    @FXML
    BorderPane borderPane ;


    @FXML
    public void signup() throws IOException {
        Image image = new Image("/resource/avatar.png");
        Notifications notifications = Notifications.create()
                .title("Successfully")
                .text("You just have resigtered!")
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {System.out.println("hello");

                });
        notifications.show();
        Parent root = FXMLLoader.load(getClass().getResource("/main/view/dashboardView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void show() throws IOException {
        System.out.println("thanh tai nguyen");
        Parent root ;
        root = FXMLLoader.load(getClass().getResource("/main/view/grammarView.fxml"));
        borderPane.setCenter(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
