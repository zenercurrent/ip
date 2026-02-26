package zener;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import zener.abstractions.Parser;
import zener.abstractions.Storage;
import zener.exceptions.InvalidTaskException;
import zener.exceptions.UnknownCommandException;
import zener.tasks.Task;
import zener.tasks.TaskList;
import zener.ui.Ui;

/**
 * The ZenerBot client.
 * <p>
 * Built based on the singleton pattern; use {@link #getInstance()} to get the instance,
 * then use {@link #run(Ui ui)} to start the bot loop.
 * Everything else (termination, command parsing, user input, etc.) is handled by the Zenerbot class.
 */
public class Zenerbot {
    /** The welcome logo of Zenerbot! */
    public static final String LOGO =
                                                               """
                                                               \s
                                              ▄▄               \s
                                              ██           ██  \s
                ▀▀▀██ ▄█▀█▄ ████▄ ▄█▀█▄ ████▄ ████▄ ▄███▄ ▀██▀▀\s
                  ▄█▀ ██▄█▀ ██ ██ ██▄█▀ ██ ▀▀ ██ ██ ██ ██  ██  \s
                ▄██▄▄ ▀█▄▄▄ ██ ██ ▀█▄▄▄ ██    ████▀ ▀███▀  ██  \s
                                                               \s
                                                               \s""";

    /** The singleton instance of the bot */
    private static Zenerbot bot;

    /** Used by the bot to indicate if it is still initialising. (true if yes) */
    private static boolean initMode = true;

    private final TaskList tasks;
    private final Storage storage;
    private final Parser parser;
    private Ui ui;


    private Zenerbot() {
        this.tasks = new TaskList();
        this.storage = new Storage("./data/zener.txt");
        this.parser = new Parser();
    }

    /**
     * Gets the singleton instance of the bot.
     * <a href="https://www.baeldung.com/java-singleton">see Java Singleton</a>
     *
     * @return the bot instance
     */
    public static Zenerbot getInstance() {
        if (bot == null) {
            bot = new Zenerbot();
        }
        return bot;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public boolean isInit() {
        return Zenerbot.initMode;
    }

    /**
     * Saves the current tasks to Storage.
     */
    public void save() {
        this.storage.save(this.tasks);
    }

    /**
     * Prints the given msg using the UI abstraction.
     *
     * @param msg the message to send
     */
    public void print(String msg) {
        this.ui.consoleMessage(msg);
    }

    /**
     * Parses the raw string command into an executable Command object <b>and executes it</b>.
     * Deals with exceptions caused by invalid commands or wrong usage.
     *
     * @param raw the raw command string
     * @return true if success, false otherwise
     */
    public boolean exec(String raw) {
        Command command;
        try {
            command = parser.getCommand(raw);
            String[] parameters = parser.getParameters(raw);
            if (!initMode || command.isScriptable()) {
                command.execute(Zenerbot.getInstance(), parameters);
                return true;
            } else {
                return false;
            }

        } catch (UnknownCommandException | InvalidTaskException e) {
            if (!initMode) {
                ui.consoleError(e.getMessage());
            }
            return false;
        }
    }

    /**
     * Starts and runs the bot, with the provided UI setup to define the medium of communication.
     *
     * @param ui the ui
     */
    public void run(Ui ui) {
        this.ui = ui;

        // pre-initialisation
        Zenerbot.initMode = true;
        ui.consoleMessage("Initialising bot...\nLoading from previous save...");

        // loading from save file
        try {
            storage.load(this.tasks);
        } catch (IOException e) {
            ui.consoleError("File was unable to be created due to an unknown reason.\nLoading unsuccessful.\n");
        }

        // intro
        Zenerbot.initMode = false;
        ui.welcomeMessage();

        Command.LIST.execute(this, new String[]{}); // show list

        // bot loop (continues until termination)
        // update: waits for ui.handleUserInput instead
    }

    /** Terminates the bot. */
    public void terminate() {
        this.save(); // just in case..

        ui.goodbyeMessage();
        // wait 3 seconds for user to see message
        try {
            Thread.sleep(3000); // Pause for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        System.exit(0); // goodbye
    }
}
