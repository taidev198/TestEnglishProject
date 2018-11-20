package controller;

import animatefx.animation.FadeIn;
import helper.LoadSceneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GrammarModel;
import model.TestGrammarModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminGrammarController implements Initializable, LoadSceneHelper {

    @FXML
    TableView<GrammarModel.Grammar> tableView;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> idCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> descriptionCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> contentCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, MenuButton> actionCol;

    @FXML
    TextField idText;
    @FXML
    TextField descriptionText;
    @FXML
    TextField contentText;
    @FXML
    TextField searchingField;
    @FXML
    Text filterText;
    FilteredList<GrammarModel.Grammar> filteredList;
    private  List<List<String>> listGrammar;


    AnchorPane anchorPane;
    @FXML
    GrammarModel model;
    ObservableList<GrammarModel.Grammar> data;
    List<MenuButton> menuButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    model = new GrammarModel();
        loadData();
        filter();
    }

    @FXML
    private void addRow(){
        menuButton.add(new MenuButton());
        MenuItem edit = new MenuItem("edit");
        MenuItem delete = new MenuItem("delete");
        edit.setOnAction(event -> {
            editableCols();
            System.out.println("edit");
        });
        delete.setOnAction(event -> {
            removeSelectedRows(Integer.parseInt(listGrammar.get(0).get(Integer.parseInt((tableView.getSelectionModel().getSelectedItem().getId())))), tableView.getSelectionModel().getSelectedIndex());
        });
        menuButton.get(menuButton.size() -1).setText("...");
        menuButton.get(menuButton.size() -1).getItems().addAll(edit);
        menuButton.get(menuButton.size() -1).getItems().addAll(delete);
        data.addAll(new GrammarModel.Grammar(
                idText.getText(),descriptionText.getText(),contentText.getText(), menuButton.get(menuButton.size() -1))
        );
        model.addGrammar(new GrammarModel.Grammar(
                idText.getText(),descriptionText.getText(),contentText.getText(), menuButton.get(menuButton.size() -1)));

        idText.clear();
        descriptionText.clear();
        contentText.clear();


    }

    private void loadScene(String view){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(view));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(parent);
            new FadeIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void switchScene(String url, Object parent) {

    }

    @Override
    public void loadData() {
        data = FXCollections.observableArrayList();
        idCol.setCellValueFactory( new PropertyValueFactory<>("id"));
        descriptionCol.setCellValueFactory( new PropertyValueFactory<>("des"));
        contentCol.setCellValueFactory( new PropertyValueFactory<>("content"));
        actionCol.setCellValueFactory( new PropertyValueFactory<>("menuButton"));
        listGrammar = model.getGrammar();
        menuButton = new ArrayList<>();
        for (int i = 0; i < listGrammar.get(0).size(); i++) {
            menuButton.add(new MenuButton());
            MenuItem edit = new MenuItem("edit");
            MenuItem delete = new MenuItem("delete");
            menuButton.get(i).setText("...");
            menuButton.get(i).getItems().addAll(edit);
            menuButton.get(i).getItems().addAll(delete);
            int id = i;
            edit.setOnAction(event -> {
                OnEdit(false);
                System.out.println("edit");
            });
            delete.setOnAction(event -> {
                System.out.println(id);
                removeSelectedRows(Integer.parseInt(listGrammar.get(0).get(id)), tableView.getSelectionModel().getSelectedIndex());
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            });

            data.addAll(new GrammarModel.Grammar(listGrammar.get(0).get(i),
                   listGrammar.get(1).get(i), listGrammar.get(2).get(i), menuButton.get(i)) );
            tableView.setItems(data);
            tableView.setRowFactory(param -> new rowGrammar());

        }
    }

    private void removeSelectedRows(int parseInt, int selectedIndex) {
        model.deleteGrammar(parseInt);
        data.remove(selectedIndex);
    }

    private void editableCols() {

    }

    private void filter() {
        filteredList = new FilteredList<>(tableView.getItems(), questionTableView -> true);
        searchingField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(grammar  -> {
                filterText.setVisible(false);
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (grammar.getDes().contains(lowerCaseFilter))
                    return true;
                else return grammar.getContent().contains(lowerCaseFilter);
            });
        });
        filterText.setOnKeyPressed(event ->{
            if (event.getEventType() != null)
                filterText.setVisible(false);
        } );

        SortedList<GrammarModel.Grammar> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

    public static class rowGrammar extends TableRow<GrammarModel.Grammar>{
        private Label id;
        private Label des;
        private Text content;

        public rowGrammar( ){
            super();
            this.id = new Label();
            this.des = new Label();
            this.content = new Text();
        }

        @Override
        protected void updateItem(GrammarModel.Grammar item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty){
                id.setText(item.getId());
                des.setText(item.getDes());
                content.setText(item.getContent());
            }else setGraphic(null);
        }

        @Override
        public void commitEdit(GrammarModel.Grammar newValue) {
            GrammarModel.Grammar currentItem = getItem();
            if (currentItem == null || newValue == null || !Objects.equals(newValue, currentItem)) {
                super.commitEdit(newValue);
            } else {
                // if the string is the same, keep the old value
                super.commitEdit(currentItem);
            }
        }
    }

    public void OnEdit(boolean isEditable){
        final Stage dialog = new Stage();
        dialog.setTitle("RESULT");
        Button update = new Button("UPDATE");
        Button add = new Button("ADD");
        AnchorPane root = new AnchorPane();

        dialog.initModality(Modality.NONE);
        dialog.initOwner(tableView.getScene().getWindow());

        VBox dialogVbox1 = new VBox(30);
        dialogVbox1.setAlignment(Pos.CENTER);
        dialogVbox1.setFillWidth(true);
        dialogVbox1.setPadding(new Insets(50, 0, 0, 70));

        HBox idHbox = new HBox(90);
        Label idlabel = new Label("ID");
        TextField idText = new TextField();
        idText.setPromptText("ID");
        idHbox.getChildren().addAll(idlabel, idText);

        HBox desHbox = new HBox(10);
        Label deslabel = new Label("DESCRIPTION");
        TextField desAText = new TextField();
        desAText.setPromptText("DESCRIPTION");
        desHbox.getChildren().addAll(deslabel, desAText);

        HBox contentHbox = new HBox(35);
        Label contentlabel = new Label("CONTENT");
        TextArea contentText = new TextArea();
        contentText.setPromptText("CONTENT");
        contentText.setPrefWidth(500);

        contentHbox.setPrefHeight(500);
        contentHbox.getChildren().addAll(contentlabel, contentText);

        update.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

                });
        add.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {


                });
        dialogVbox1.getChildren().addAll(idHbox);
        dialogVbox1.getChildren().addAll(desHbox);
        dialogVbox1.getChildren().addAll(contentHbox);

        if (isEditable)
            dialogVbox1.getChildren().addAll(update);
        else dialogVbox1.getChildren().addAll(add);


        root.getChildren().addAll(dialogVbox1);
        Scene dialogScene = new Scene(root, 700, 800);
        dialog.setScene(dialogScene);
        root.setStyle(
                "   -fx-background-color: rgb(58,69,88);\n" +
                        "    -fx-background-radius: 0px;\n" +
                        "    -fx-text-fill: #b8b1b1;\n");
        update.setStyle("-fx-background-color: rgb(22,169,250);\n" +
                "    -fx-background-radius: 0px;\n" +
                "    -fx-text-fill: #0099ff;");
        add.setStyle("-fx-background-color: rgb(22,169,250);\n" +
                "    -fx-background-radius: 0px;\n" +
                "    -fx-text-fill: #0099ff;");
        dialog.show();
    }
}
