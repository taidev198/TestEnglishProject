package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainContentController implements Initializable {
    @FXML
    private TextArea content;
    @FXML
    private AnchorPane pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //content.setText("thanh tai nguyen");
        this.content.setWrapText(true);
        this.content.setEditable(false);
        this.content.setFocusTraversable(false);
    }

    public void setContent(String title){
        switch (title){
            case "1":

                this.content.setText("As, when and while are conjunctions. In some uses as, when and while can mean the same, but they can also have slightly different meanings. We use them to introduce subordinate clauses.\n" +
                    "\n" +
                    "We can use as, when and while to mean ‘during the time that’, to connect two events happening at the same time:\n" +
                    "\n" +
                    "Another coach-load of people arrived as we were leaving.\n" +
                    "\n" +
                    "We often use them with the past continuous to refer to background events:\n" +
                    "\n" +
                    "When the men were out working in the field, I helped with milking the cows, feeding the calves and the pigs.\n" +
                    "\n" +
                    "While he was working, he often listened to music.\n" +
                    "\n" +
                    "We can put clauses with as, when and while before or after the main clause. When they come before the main clause, we use a comma:\n" +
                    "\n" +
                    "As she was leaving the court, a crowd of photographers gathered around her. (before the main clause, followed by a comma)\n" +
                    "\n" +
                    "Steven was very unhappy when things weren’t going well for him.\n" +
                    "\n" +
                    "Lucy came into the room while he was waiting.\n" +
                    "\n" +
                    " \n" +
                    "As\n" +
                    "We can use as to introduce two events happening at the same time. After as, we can use a simple or continuous form of the verb. The continous form emphasises an action that interrupts or occurs during the progress of another action:\n" +
                    "\n" +
                    "As she walked to the door, she thanked them for a lovely dinner.\n" +
                    "\n" +
                    "As they were signing the contract, they noticed that a page was missing.\n" +
                    "\n" +
                    " \n" +
                    "When\n" +
                    "We can use when to introduce a single completed event that takes place in the middle of a longer activity or event. In these cases, we usually use a continuous verb in the main clause to describe the background event:\n" +
                    "\n" +
                    "He was walking back to his flat when he heard an explosion.\n" +
                    "\n" +
                    "Depending on the context, when can mean ‘after’ or ‘at the same time’.\n" +
                    "\n" +
                    "Compare\n" +
                    "When you open the file, check the second page.\n" +
                    "\n" +
                    "when meaning ‘after’\n" +
                    "\n" +
                    "I eat ice cream when I am on holiday.\n" +
                    "\n" +
                    "when meaning ‘at the same time’\n" +
                    "\n" +
                    "See also:\n" +
                    "\n" +
                    "Conditionals\n" +
                    "\n" +
                    "We often use just with when or as to express things happening at exactly the same time:\n" +
                    "\n" +
                    "The phone always rings just when I’m closing the front door.\n" +
                    "\n" +
                    "She was a brilliant gymnast, but she had a terrible accident in 1999, just as her career was taking off.\n" +
                    "\n" +
                    " \n" +
                    "While and as\n" +
                    "We can use while or as to talk about two longer events or activities happening at the same time. We can use either simple or continuous verb forms:\n" +
                    "\n" +
                    "We spent long evenings talking in my sitting-room while he played the music he had chosen and explained his ideas.\n" +
                    "\n" +
                    "We were lying on the beach sunbathing as they were playing volleyball.\n" +
                    "\n" +
                    "See also:\n" +
                    "\n" +
                    "As\n" +
                    "\n" +
                    "While\n" +
                    "\n" +
                    " \n" +
                    "When and while without a subject\n" +
                    "We can use when and while without a verb, or without a subject + auxiliary verb be:\n" +
                    "\n" +
                    "Go past the village signpost and you get to a church. When there, take the next turning right. (formal)\n" +
                    "\n" +
                    "He read his book while waiting for the bus. (while he was waiting)\n" +
                    "\n" +
                    "Warning:\n" +
                    "We can’t use as in this way:\n" +
                    "\n" +
                    "We ate our sandwiches as we walked around the park.\n" +
                    "\n" +
                    "Not: … as walking around the park.\n" +
                    "\n" +
                    "See also:\n" +
                    "\n" +
                    "When\n" +
                    "\n" +
                    "While");
        }

    }
}
