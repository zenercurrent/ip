package zener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import zener.ui.DialogBox;
import zener.ui.DialogContainer;
import zener.ui.Ui;

/** JavaFX Application Main */
public class Main extends Application {

    private ScrollPane scrollPane;
    private DialogContainer dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Zenerbot bot;

    @Override
    public void start(Stage stage) {
        // Setting up requirements
        scrollPane = new ScrollPane();
        dialogContainer = new DialogContainer();
        scrollPane.setContent(dialogContainer);

        Ui ui = new Ui(dialogContainer);
        bot = Zenerbot.getInstance();
        bot.run(ui);      // important step

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        // Formatting window
        stage.setTitle("ZenerBot");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Scroll down on new dialog
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        // Handle user input
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        stage.setScene(scene);
        stage.show();
    }

    private void handleUserInput() {
        String input = userInput.getText();

        dialogContainer.getChildren().addAll(DialogBox.createUserDialog(input));
        userInput.clear();

        // get response from bot
        bot.exec(input);
    }
}
