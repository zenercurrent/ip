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
        assert dialogContainer != null : "Dialog Container was not injected. Check FXML";
        assert scrollPane != null : "Scroll Pane was not injected. Check FXML";
        assert userInput != null : "User Input was not injected. Check FXML";
        assert sendButton != null : "Send Button was not injected. Check FXML";

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

