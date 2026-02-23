import java.util.Scanner;

/**
 * Handles the interactions and display with user. Including displaying of messages, errors, etc..
 * <p>
 * Note: the whole point is to replace raw System.out/in and have an abstraction layer for UI instead.
 */
public class Ui {
    private final String DIVIDER = "------------------------------";

    /**
     * Displays a divider (to separate outputs).
     */
    public void divider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays the set welcome message!
     */
    public void welcomeMessage() {
        System.out.println(Zenerbot.LOGO);
        divider();
        System.out.println("Hello there! I am ZenerBot, your personal assistant.");
        System.out.println("How can I help?");
    }

    /**
     * Displays the set goodbye message!
     */
    public void goodbyeMessage() {
        System.out.println("Goodbye! It was a nice chat! :)");

        System.out.println(Zenerbot.LOGO);
        System.out.println("Isaac Goh");
    }

    /**
     * Sends a console message.
     * Eg: loading messages, for user's info
     */
    public void consoleMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message.
     */
    public void consoleError(String message) {
        // for now, no differentiation. could be different in the future.
        System.out.println(message);
    }
}
