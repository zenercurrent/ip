package zener.ui;

import zener.Zenerbot;

/**
 * Handles the interactions and display with user. Including displaying of messages, errors, etc..
 * <p>
 * Note: the whole point is to replace raw System.out/in and have an abstraction layer for UI instead.
 */
public class Ui {
    @Deprecated
    private static final String DIVIDER = "------------------------------";

    private final DialogContainer dialogContainer;

    public Ui(DialogContainer dialogContainer) {
        this.dialogContainer = dialogContainer;
    }


    /**
     * Displays a divider (to separate outputs).
     *
     * @deprecated No longer used after migration to JavaFX UI.
     */
    @Deprecated
    public void divider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays the set welcome message!
     */
    public void welcomeMessage() {
        System.out.println(Zenerbot.LOGO);
        dialogContainer.addDialog(
                DialogBox.createBotDialog("Hello there! I am ZenerBot, your personal assistant.\nHow can I help?"));
    }

    /**
     * Displays the set goodbye message!
     */
    public void goodbyeMessage() {
        dialogContainer.addDialog(DialogBox.createBotDialog("Goodbye! It was a nice chat! :)"));

        System.out.println(Zenerbot.LOGO);
        System.out.println("Isaac Goh");
    }

    /**
     * Sends a console message.
     * Eg: loading messages, for user's info
     */
    public void consoleMessage(String message) {
        var dbox = DialogBox.createBotDialog(message);
        dialogContainer.addDialog(dbox);
    }

    /**
     * Displays an error message.
     */
    public void consoleError(String message) {
        // for now, no differentiation. could be different in the future.
        var dbox = DialogBox.createBotDialog(message);
        dialogContainer.addDialog(dbox);
    }
}
