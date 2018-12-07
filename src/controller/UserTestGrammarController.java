package controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TestGrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;


/**Clone primitive list:https://stackoverflow.com/questions/8441664/how-do-i-copy-the-contents-of-one-arraylist-into-another/29033799
 * custom dialog:https://stackoverflow.com/questions/40031632/custom-javafx-dialog
 * Focus a table row dynamically:https://stackoverflow.com/questions/20413419/javafx-2-how-to-focus-a-table-row-programmatically/20433947*/
public class UserTestGrammarController implements Initializable {

    @FXML
    ScrollPane rootLayout;
    @FXML
    ListView<Label> grammarLists;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text welcomeText;
    @FXML
    Button submit;
    @FXML
    Text description;
    private List<String[]> answer;
    private String[] clone;

    private TestGrammarModel model;
    private Map<String, List<List<String>>> listQuestion;
    List<Map.Entry<String, List<List<String>>>> entryList;
    List<String> key = new ArrayList<>();
    Map<Integer, List<RadioButton>> listButton = new HashMap<>();
    Boolean isSubmited ;
    Integer selectedIdx ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setVisible(false);
        submit.setOnAction(event -> {
            if (!isSubmited) {
                submit.setText("RESTART");
                OnSubmit();
                // this.isSubmited = true;
                System.out.println("submit");
            }
            else {
                answer.set(selectedIdx, clone);
                System.out.println(Arrays.toString(answer.get(0)));
                //refresh listview when user restart contest.
                this.isSubmited = false;
                submit.setVisible(true);

                createQuestion(entryList.get(selectedIdx).getValue());
                System.out.println("restart");
                submit.setText("SUBMIT");
            }
        });
        model = new TestGrammarModel();
        listQuestion = model.getTestGrammarFollowGrammarId();
        entryList = new ArrayList<>(listQuestion.entrySet());
        isSubmited = true;
        for (Map.Entry<String, List<List<String>>> entry :
               entryList ) {
            Label label = new Label(entry.getKey() );
            key.add(entry.getKey());
            label.setGraphic( new ImageView( new Image("/resource/avatar.png")));
            grammarLists.getItems().add(label);
        }
        answer = new ArrayList<>();

       addListenerToListView();
    }

    private void createQuestion(List<List<String>>  listQuestion) {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        rootLayout.setPadding(new Insets(10, 10, 10, 10));
        flowPane.setVgap(10);
        flowPane.setHgap(4);
        flowPane.setPrefWrapLength(310);
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            //init question and add event handler on each questions.
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
            question = new Text(i+": " +listQuestion.get(1).get(i));
            optionA = new RadioButton("A " + listQuestion.get(2).get(i));
            optionB = new RadioButton("B " + listQuestion.get(3).get(i));
            optionC = new RadioButton("C " + listQuestion.get(4).get(i));
            optionD = new RadioButton("D " + listQuestion.get(5).get(i));
            List<RadioButton> list = new ArrayList<>();
            list.add(optionA);
            list.add(optionB);
            list.add(optionC);
            list.add(optionD);
            listButton.put(i,list);
            number = new Text();
            content = new HBox(number, question);
            choose= new HBox(  optionA, optionB ,
                    optionC ,optionD);
            content.setPadding(new Insets(0,0,0,10));
            HBox.setMargin(optionA, new Insets(40, 80, 30, 70));
            HBox.setMargin(optionB, new Insets(40, 80, 30, 70));
            HBox.setMargin(optionC, new Insets(40, 80, 30, 70));
            HBox.setMargin(optionD, new Insets(40, 80, 30, 70));
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
            optionA.setUserData(listQuestion.get(2).get(i));
            optionB.setUserData(listQuestion.get(3).get(i));
            optionC.setUserData(listQuestion.get(4).get(i));
            optionD.setUserData(listQuestion.get(5).get(i));

            choose.setAlignment(Pos.CENTER);
            vBox = new VBox(content, choose);
            vBox.setSpacing(10);
            vBox.setPrefWidth(1100);
            int finalI = i;
            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (group.getSelectedToggle() != null){
                    if (optionA.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "A";
                        System.out.println(finalI + " "+optionA.getUserData());
                    }
                    else if (optionB.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "B";
                        System.out.println(finalI + " "+optionB.getUserData());
                    }
                    else  if (optionC.isSelected()) {
                        answer.get(selectedIdx)[finalI]  = "C";
                        System.out.println(finalI + " "+optionC.getUserData());
                    }
                    else {
                        answer.get(selectedIdx)[finalI]  = "D";
                        System.out.println(finalI + " "+optionD.getUserData());
                    }
                }
                if (answer.get(selectedIdx)[finalI].equals(listQuestion.get(6).get(finalI))){
                    answer.get(selectedIdx)[answer.get(selectedIdx).length -2] =
                            Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length -2 ])+1);
                    answer.get(selectedIdx)[answer.get(selectedIdx).length -1] =
                            Integer.toString(Integer.parseInt(answer.get(selectedIdx)[answer.get(selectedIdx).length-1]) -1) ;
                }
                System.out.println(Arrays.toString(answer.get(selectedIdx)));

            });
            vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 10, 0.5, 0.0, 0.0);" +
                    "-fx-background-color: white;" + "-fx-background-radius: 7;"); // Shadow effect
            //vBox.setEffect(new DropShadow(2d, 0d, +2d, Color.WHITE));
            flowPane.getChildren().add(vBox);
        }

        flowPane.setPadding(new Insets(5, 0, 5, 0));
        //    flowPane.setEffect(new DropShadow(2d, 0d, +2d, new Color("#D1D7DB")));
        rootLayout.setContent(flowPane);
    }

    private void checkAns(){
        List<List<String>> tmp = entryList.get(selectedIdx).getValue();
        List<Map.Entry<Integer, List<RadioButton>>> entryList = new ArrayList<>(listButton.entrySet());
        for (Map.Entry<Integer, List<RadioButton>> entry:
                entryList ) {
            if (answer.get(selectedIdx)[entry.getKey()] != null) {
                switch (answer.get(selectedIdx)[entry.getKey()]) {
                    case "A":
                        entry.getValue().get(0).setSelected(true);
                        entry.getValue().get(0).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "B":
                        entry.getValue().get(1).setSelected(true);
                        entry.getValue().get(1).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "C":
                        entry.getValue().get(2).setSelected(true);
                        entry.getValue().get(2).setStyle("-fx-background-color: #FF6A53");
                        break;
                    case "D":
                        entry.getValue().get(3).setSelected(true);
                        entry.getValue().get(3).setStyle("-fx-background-color: #FF6A53");
                        break;
                }
            }
            if (answer.get(selectedIdx)[entry.getKey()] != null) {
                switch (tmp.get(6).get(entry.getKey())) {
                    case "A":
                        entry.getValue().get(0).setStyle("-fx-background-color: #499C54");
                        break;
                    case "B":

                        entry.getValue().get(1).setStyle("-fx-background-color: #499C54");
                        break;
                    case "C":

                        entry.getValue().get(2).setStyle("-fx-background-color: #499C54");
                        break;
                    case "D":

                        entry.getValue().get(3).setStyle("-fx-background-color: #499C54");
                        break;
                }
            }
        }
    }

    private void addListenerToListView(){
        grammarLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            welcomeText.setVisible(false);
            if (this.isSubmited){
                submit.setText("SUBMIT");
                submit.setVisible(true);
                selectedIdx = grammarLists.getSelectionModel().getSelectedIndex();
                System.out.println(selectedIdx);
                this.isSubmited = false;
                List<List<String>> tmp = entryList.get(selectedIdx).getValue();
                System.out.println(tmp);
                createQuestion(tmp);
                initQuestion();
                clone = saveList(answer.get(selectedIdx));
                grammarLists.setFocusTraversable( false );
            }else {
                grammarLists.requestFocus();
               // grammarLists.getSelectionModel().select(selectedIdx);
               // grammarLists.getFocusModel().focus(selectedIdx);
            }
        });
    }

    private void initQuestion(){
        for (int i = 0; i < entryList.size(); i++) {
            List<List<String>> tmp = entryList.get(i).getValue();
            answer.add(new String[tmp.get(1).size() +2]);
            answer.get(i)[tmp.get(1).size()+1] = String.valueOf(tmp.get(1).size());
            answer.get(i)[tmp.get(1).size()] = String.valueOf("0");
        }
    }

    private String[] saveList(String[] list){

        String[] newList = new String[list.length];
        System.out.println("list:" + Arrays.toString(list));
            newList[list.length-1] = String.valueOf(list.length-2);
            newList[list.length-2] = String.valueOf("0");

        return newList;
    }

    @FXML
    private void back(){
        final Stage dialog = new Stage();
        dialog.setTitle("Confirmation");
        Button back = new Button("BACK");
        Button no = new Button("NO");

        Label displayLabel = new Label("What do you want to do ?");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner( grammarLists.getScene().getWindow());

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        VBox dialogVbox1 = new VBox(20);
        dialogVbox1.setAlignment(Pos.CENTER_LEFT);

        VBox dialogVbox2 = new VBox(20);
        dialogVbox2.setAlignment(Pos.CENTER_RIGHT);

        dialogHbox.getChildren().add(displayLabel);
        dialogVbox1.getChildren().add(back);
        dialogVbox2.getChildren().add(no);

        back.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        // inside here you can use the minimize or close the previous stage//
                        dialog.close();
                        try {
                            Parent parent = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
                            anchorPane.getChildren().clear();
                            anchorPane.getChildren().addAll(parent);
                            new FadeIn(parent).play();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("Main view");
                    }
                });
        no.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                    dialog.close();
                    submit.setVisible(true);
                });

        dialogHbox.getChildren().addAll(dialogVbox1, dialogVbox2);
        Scene dialogScene = new Scene(dialogHbox, 500, 200);
        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);
    }


    @FXML
    public void OnSubmit(){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button review = new Button("REVIEW");
        Button restart = new Button("RESTART");
        //init piechart
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        PieChart res = new PieChart();
        int numberOfWrong = Integer.valueOf( answer.get(selectedIdx)[answer.get(selectedIdx).length -1]);
        int numberOfCorrect = Integer.valueOf( answer.get(selectedIdx)[answer.get(selectedIdx).length -2]);
        list.addAll(new PieChart.Data("Correct :" +numberOfCorrect +" question", numberOfCorrect),
                new PieChart.Data("Wrong :" + numberOfWrong + " question", numberOfWrong));
        res.setData(list);
        res.setStartAngle(90);

        res.setTitle("OverView");
        res.setLegendSide(Side.TOP);


        Label displayLabel = new Label("What do you want to do ?");
        displayLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        dialog.initModality(Modality.NONE);
        dialog.initOwner(submit.getScene().getWindow());

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        VBox dialogVbox1 = new VBox(20);
        dialogVbox1.setAlignment(Pos.CENTER);

        //add children
        dialogVbox1.getChildren().addAll(res);
        dialogVbox1.getChildren().addAll(displayLabel);
        dialogVbox1.getChildren().addAll(dialogHbox);
        dialogHbox.getChildren().add(review);
        dialogHbox.getChildren().add(restart);
        review.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    checkAns();
                    this.isSubmited = true;
                    answer.set(selectedIdx, saveList(clone))  ;
                    // inside here you can use the minimize or close the previous stage//
                    dialog.close();
                    submit.setVisible(true);
                    grammarLists.setFocusTraversable( true );
                });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    answer.set(selectedIdx, saveList(clone))  ;
                    //refresh listview when user restart contest.
                    dialog.close();
                    this.isSubmited = false;
                    submit.setVisible(true);
                    grammarLists.setFocusTraversable( true );
                    answer.set(selectedIdx, clone);
                    System.out.println(Arrays.toString(answer.get(0)));
                    //refresh listview when user restart contest.
                    createQuestion(entryList.get(selectedIdx).getValue());

                });
        Scene dialogScene = new Scene(dialogVbox1, 600, 450);
//        dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
        submit.setVisible(false);

    }

}
