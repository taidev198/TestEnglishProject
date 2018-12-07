package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;

/**
 * Created by traig on 7:09 PM, 12/5/2018
 */
/**Flowpane inside scrollpane: https://www.programcreek.com/java-api-examples/?api=javafx.scene.layout.FlowPane
 * adding effect onto flowpane:https://stackoverflow.com/questions/17571593/drop-shadow-in-an-undecorated-pane-javafx*/
public class TestTemplateController implements Initializable {

    @FXML
    ListView<String> listContest;
    boolean isSelected = false;
    @FXML
    ScrollPane rootLayout;
    Map<Integer, List<RadioButton>> listButton = new HashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        rootLayout.setPadding(new Insets(10, 10, 10, 10));
        flowPane.setVgap(10);
        flowPane.setHgap(4);
        flowPane.setPrefWrapLength(310);
        listContest.getItems().addAll(Arrays.asList("Tai","Tai","Tai","Tai","Tai","Tai","Tai","Tai"));


        //init question and add event handler on each questions.
        for (int i = 0; i < 15; i++) {
            HBox content;
            HBox choose;
            Text question;
            RadioButton optionA;
            RadioButton optionB;
            RadioButton optionC;
            RadioButton optionD;
            Text number;
            VBox vBox;
            ToggleGroup group;
            question = new Text(i +" :WARNING: Loading FXML document with JavaFX API of version 10.0.1 by JavaFX runtime of version 8.0.181");
            optionA = new RadioButton("A");
            optionB = new RadioButton("B");
            optionC = new RadioButton("C");
            optionD = new RadioButton("D");
            List<RadioButton> list = new ArrayList<>();
            list.add(optionA);
            list.add(optionB);
            list.add(optionC);
            list.add(optionD);
            listButton.put(i,list );
            number = new Text();
            content = new HBox(number, question);
            choose= new HBox(  optionA, optionB ,
                    optionC ,optionD);
            content.setPadding(new Insets(0,0,0,10));
            HBox.setMargin(optionA, new Insets(40, 100, 30, 70));
            HBox.setMargin(optionB, new Insets(40, 100, 30, 70));
            HBox.setMargin(optionC, new Insets(40, 100, 30, 70));
            HBox.setMargin(optionD, new Insets(40, 100, 30, 70));
            group = new ToggleGroup();
            optionA.setFont(Font.font(18));
            optionB.setFont(Font.font(18));
            optionC.setFont(Font.font(18));
            optionD.setFont(Font.font(18));
            question.setFont(Font.font(18));
            number.setFont(Font.font(18));
            optionB.setToggleGroup(group);
            optionA.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);

            //set user date
            optionA.setUserData("A");
            optionB.setUserData("A");
            optionC.setUserData("A");
            optionD.setUserData("A");

            choose.setAlignment(Pos.CENTER);
            vBox = new VBox(content, choose);
            vBox.setSpacing(10);
            vBox.setPrefWidth(1100);
            int finalI = i;
            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (group.getSelectedToggle() != null){

                    if (optionA.isSelected())
                        System.out.println("a"+ finalI);
                     else if (optionB.isSelected())
                            System.out.println("b" + finalI);
                     else  if (optionC.isSelected())
                            System.out.println("c" + finalI);
                        else System.out.println("d" + finalI);
                    }
            });

            vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);" +
                    "-fx-background-color: white;"); // Shadow effect
            //vBox.setEffect(new DropShadow(2d, 0d, +2d, Color.WHITE));
            flowPane.getChildren().add(vBox);
        }
        flowPane.setPadding(new Insets(5, 0, 5, 0));
    //    flowPane.setEffect(new DropShadow(2d, 0d, +2d, new Color("#D1D7DB")));
        rootLayout.setContent(flowPane);
        listContest.setFocusTraversable(false);
    }



    @FXML
    private void OnSubmit(){
        //isSelected = true;
        listButton.get(0).get(0).setStyle("-fx-background-color: #FF6A53");
        System.out.println(listButton.get(0).get(0).getUserData());

    }
}
