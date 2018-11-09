package controller;

import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginGmailController implements Initializable {

//    @FXML
//    WebView webView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=621338506311-nooffe8l9oi6sir2on0n4d3ao30tm03r.apps.googleusercontent.com&redirect_uri=http://localhost:8888/Callback&response_type=code&scope=https://www.googleapis.com/auth/gmail.labels");
        System.out.println("thanh tai nguyen");
    }
}
