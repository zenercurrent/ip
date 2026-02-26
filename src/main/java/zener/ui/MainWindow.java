package zener.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import zener.Zenerbot;

/**
 * The root window of the UI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private DialogContainer dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Zenerbot bot;

    /**
     * Initialize the UI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        bot = Zenerbot.getInstance();
    }

    /**
     * Gets the user input and retrieve bot response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        dialogContainer.getChildren().addAll(DialogBox.createUserDialog(input));
        userInput.clear();

        // get response from bot
        bot.exec(input);
    }
}

