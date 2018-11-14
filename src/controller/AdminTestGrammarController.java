package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import model.TestGrammarModel;

import java.net.URL;
import java.util.*;

/**How to fix bug at TableColumn<nameClass, Integer> but TableView using String in column
 * https://stackoverflow.com/questions/27468546/how-to-use-simpleintegerproperty-in-javafx</>
 * -editable tableview with dynamic :http://java-buddy.blogspot.com/2013/03/javafx-editable-tableview-with-dynamic.html
 * --- auto commit when user click outside :https://stackoverflow.com/questions/23632884/how-to-commit-when-clicking-outside-an-editable-tableview-cell-in-javafx
 * */
public class AdminTestGrammarController implements Initializable {

    @FXML
    TableView<TestGrammarModel.QuestionTableView> tableView;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> idCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> questionCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionACol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionBCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionCCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> optionDCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> keyCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, String> grammarIdCol;
    @FXML
    TableColumn<TestGrammarModel.QuestionTableView, MenuButton> actionCol;

    MenuButton menuButton[];
    TestGrammarModel model;
    private  List<List<String>> listQuestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new TestGrammarModel();

        Callback<TableColumn<TestGrammarModel.QuestionTableView, String>, TableCell<TestGrammarModel.QuestionTableView, String>> cellFactory =
                new Callback<TableColumn<TestGrammarModel.QuestionTableView, String>, TableCell<TestGrammarModel.QuestionTableView, String>>() {
                    @Override
                    public TableCell<TestGrammarModel.QuestionTableView, String> call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        tableView.setEditable(true);
        //set celCellValueFactory for each cols.
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("number"));
        idCol.setCellFactory(cellFactory);

        questionCol.setCellValueFactory(
                new PropertyValueFactory<>("question"));
        optionACol.setCellValueFactory(
                new PropertyValueFactory<>("optionA"));
        optionBCol.setCellValueFactory(
                new PropertyValueFactory<>("optionB"));
        optionCCol.setCellValueFactory(
                new PropertyValueFactory<>("optionC"));
        optionDCol.setCellValueFactory(
                new PropertyValueFactory<>("optionD"));
        keyCol.setCellValueFactory(
                new PropertyValueFactory<>("key"));
        grammarIdCol.setCellValueFactory(
                new PropertyValueFactory<>("grammarid"));

        actionCol.setCellValueFactory(
                new PropertyValueFactory<>("action"));

        listQuestion = model.getTestGrammar();
        menuButton = new MenuButton[listQuestion.get(0).size()];
        for (int i = 0; i < listQuestion.get(0).size(); i++) {
            menuButton[i] = new MenuButton();
            MenuItem edit = new MenuItem("edit");
            MenuItem delete = new MenuItem("delete");
            menuButton[i].setText("...");
            menuButton[i].getItems().addAll(edit);
            menuButton[i].getItems().addAll(delete);
            int id = i;
            edit.setOnAction(event -> {
                editableCols();
                System.out.println("edit");
            });
            delete.setOnAction(event -> {
                System.out.println(id);
                removeSelectedRows(Integer.parseInt(listQuestion.get(0).get(id)));
                System.out.println("remove row:" + tableView.getSelectionModel().getSelectedIndex());
            });

            tableView.getItems().addAll(new TestGrammarModel.QuestionTableView(listQuestion.get(1).get(i),
                    listQuestion.get(1).get(i), listQuestion.get(2).get(i),listQuestion.get(3).get(i),
                    listQuestion.get(4).get(i), listQuestion.get(5).get(i), "",
                    listQuestion.get(6).get(i), (listQuestion.get(0).get(i)), listQuestion.get(7).get(i) , menuButton[i]) );


        }
        tableView.setRowFactory(new Callback<TableView<TestGrammarModel.QuestionTableView>, TableRow<TestGrammarModel.QuestionTableView>>() {
            @Override
            public TableRow<TestGrammarModel.QuestionTableView> call(TableView<TestGrammarModel.QuestionTableView> param) {
                return new rowQuestion();
            }
        });
    }

    private void editableCols(){
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        idCol.setOnEditCommit(event -> event.getTableView().getItems().
//                    get(event.getTablePosition().getRow()).setNumber(event.getNewValue()));
        tableView.setEditable(true);
    }



    /**
     * Remove all selected rows.
     * no params.
     */
    public void removeSelectedRows(int id) {
      //  model.removeTestGrammar(id);
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());

        // table selects by index, so we have to clear the selection or else items with that index would be selected
    }

    public class EditingCell<S, T> extends TableCell<S, T>{
        private TextField textField;
        private boolean escapePressed=false;

        private TablePosition<S, ?> tablePos=null;
        public EditingCell(){
            this(null);
        }

        /**
         * Creates a TextFieldTableCell that provides a {@link TextField} when put
         * into editing mode that allows editing of the cell content. This method
         * will work on any TableColumn instance, regardless of its generic type.
         * However, to enable this, a {@link StringConverter} must be provided that
         * will convert the given String (from what the user typed in) into an
         * instance of type T. This item will then be passed along to the
         * {@link TableColumn#onEditCommitProperty()} callback.
         *
         * @param converter A {@link StringConverter converter} that can convert
         *      the given String (from what the user typed in) into an instance of
         *      type T.
         */
        public EditingCell(StringConverter<T> converter) {
            this.getStyleClass().add("text-field-table-cell");
            setConverter(converter);
        }

        /**
         * Provides a {@link TextField} that allows editing of the cell content when
         * the cell is double-clicked, or when
         * {@link TableView#edit(int, javafx.scene.control.TableColumn)} is called.
         * This method will only  work on {@link TableColumn} instances which are of
         * type String.
         *
         * @return A {@link Callback} that can be inserted into the
         *      {@link TableColumn#cellFactoryProperty() cell factory property} of a
         *      TableColumn, that enables textual editing of the content.
         */
        public  <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
            return forTableColumn(new DefaultStringConverter());
        }

        /**
         * Provides a {@link TextField} that allows editing of the cell content when
         * the cell is double-clicked, or when
         * {@link TableView#edit(int, javafx.scene.control.TableColumn) } is called.
         * This method will work  on any {@link TableColumn} instance, regardless of
         * its generic type. However, to enable this, a {@link StringConverter} must
         * be provided that will convert the given String (from what the user typed
         * in) into an instance of type T. This item will then be passed along to the
         * {@link TableColumn#onEditCommitProperty()} callback.
         *
         * @param converter A {@link StringConverter} that can convert the given String
         *      (from what the user typed in) into an instance of type T.
         * @return A {@link Callback} that can be inserted into the
         *      {@link TableColumn#cellFactoryProperty() cell factory property} of a
         *      TableColumn, that enables textual editing of the content.
         */
        public  <S,T> Callback<TableColumn<S,T>, TableCell<S,T>> forTableColumn(
                final StringConverter<T> converter) {
            return list -> new EditingCell<S,T>(converter);
        }


        // --- converter
        private ObjectProperty<StringConverter<T>> converter =
                new SimpleObjectProperty<StringConverter<T>>(this, "converter");

        /**
         * The {@link StringConverter} property.
         */
        public final ObjectProperty<StringConverter<T>> converterProperty() {
            return converter;
        }

        /**
         * Sets the {@link StringConverter} to be used in this cell.
         */
        public final void setConverter(StringConverter<T> value) {
            converterProperty().set(value);
        }

        /**
         * Returns the {@link StringConverter} used in this cell.
         */
        public final StringConverter<T> getConverter() {
            return converterProperty().get();
        }

        @Override
        public void startEdit() {
            if (! isEditable()
                    || ! getTableView().isEditable()
                    || ! getTableColumn().isEditable()) {
                return;
            }
            super.startEdit();

            if (isEditing()) {
                if (textField == null) {
                    textField = getTextField();
                }
                escapePressed=false;
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
                textField.selectAll();

                // requesting focus so that key input can immediately go into the
                // TextField (see RT-28132)
                textField.requestFocus();
                final TableView<S> table = getTableView();
                tablePos=table.getEditingCell();
            }
        }



        @Override
        public void commitEdit(T newValue) {
            // This block is necessary to support commit on losing focus, because
            // the baked-in mechanism sets our editing state to false before we can
            // intercept the loss of focus. The default commitEdit(...) method
            // simply bails if we are not editing...

            if (! isEditing())
                return;
            final TableView<S> table = getTableView();
            if (table != null) {
                // Inform the TableView of the edit being ready to be committed.
                TableColumn.CellEditEvent editEvent = new TableColumn.CellEditEvent(
                        table,
                        tablePos,
                        TableColumn.editCommitEvent(),
                        newValue
                );

                Event.fireEvent(getTableColumn(), editEvent);
            }

            super.cancelEdit();
            // update the item within this cell, so that it represents the new value
            updateItem(newValue, false);

            if (table != null) {
                // reset the editing cell on the TableView
                table.edit(-1, null);

                // request focus back onto the table, only if the current focus
                // owner has the table as a parent (otherwise the user might have
                // clicked out of the table entirely and given focus to something else.
                // It would be rude of us to request it back again.
                // requestFocusOnControlOnlyIfCurrentFocusOwnerIsChild(table);
            }

        }

        @Override
        public void cancelEdit() {
            if(escapePressed) {
                // this is a cancel event after escape key
                super.cancelEdit();
                setText(getString()); // restore the original text in the view
            }
            else {
                // this is not a cancel event after escape key
                // we interpret it as commit.
                String newText=textField.getText(); // get the new text from the view
                this.commitEdit(getConverter().fromString(newText)); // commit the new text to the model
            }
            setGraphic(null); // stop editing with TextField
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }


//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
//            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//                @Override
//                public void handle(KeyEvent t) {
//                    if (t.getCode() == KeyCode.ENTER) {
//                        commitEdit(textField.getText());
//                    } else if (t.getCode() == KeyCode.ESCAPE) {
//                        cancelEdit();
//                    }
//                }
//            });
//        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
        private TextField getTextField() {

            final TextField textField = new TextField(getString());

            // Use onAction here rather than onKeyReleased (with check for Enter),
            // as otherwise we encounter RT-34685
            textField.setOnAction(event -> {
                if (converter == null) {
                    throw new IllegalStateException(
                            "Attempting to convert text input into Object, but provided "
                                    + "StringConverter is null. Be sure to set a StringConverter "
                                    + "in your cell factory.");
                }
                this.commitEdit(getConverter().fromString(textField.getText()));
                event.consume();
            });
            textField.setOnKeyPressed(t -> { if (t.getCode() == KeyCode.ESCAPE) escapePressed = true; else escapePressed = false; });
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ESCAPE) {
                    // djw the code may depend on java version / expose incompatibilities:
                    throw new IllegalArgumentException("did not expect esc key releases here.");
                }
            });
            return textField;
        }

    }


    public static class rowQuestion extends TableRow<TestGrammarModel.QuestionTableView>{

        private Label id;
        private Label question;
        private Label optionA;
        private Label optionB;
        private Label optionC;
        private Label optionD;
        private Label key;
        private Label grammarid;
        private MenuButton action;

        public rowQuestion(){
            super();
            id = new Label();
            question = new Label();
            optionA = new Label();
            optionB = new Label();
            optionC = new Label();
            optionD = new Label();
            key = new Label();
            grammarid = new Label();
        }

        @Override
        protected void updateItem(TestGrammarModel.QuestionTableView item, boolean empty) {
            super.updateItem(item, empty);
            if (item !=null && !empty){
                id.setText(String.valueOf(item.getNumber()));
                question.setText(item.getQuestion());
                optionA.setText(item.getOptionA());
                optionB.setText(item.getOptionB());
                optionC.setText(item.getOptionC());
                optionD.setText(item.getOptionD());
                key.setText(item.getKey());
                grammarid.setText(item.getAns());
            }else setGraphic(null);

        }

        @Override
        public void commitEdit(TestGrammarModel.QuestionTableView newValue) {
            TestGrammarModel.QuestionTableView currentItem = getItem();
            if (currentItem == null || newValue == null || !Objects.equals(newValue, currentItem)) {
                super.commitEdit(newValue);
            } else {
                // if the string is the same, keep the old value
                super.commitEdit(currentItem);
            }
        }
    }
}
