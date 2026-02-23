package zener;

import zener.abstractions.Parser;
import zener.abstractions.Storage;
import zener.abstractions.Ui;
import zener.exceptions.InvalidTaskException;
import zener.exceptions.UnknownCommandException;
import zener.tasks.Task;
import zener.tasks.TaskList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type zener.Zenerbot.
 */
public class Zenerbot {
    /** The singleton instance of the bot */
    private static Zenerbot BOT;

    /** Used by the bot to indicate if it is still initialising. (true if yes) */
    private static boolean INIT_MODE = true;

    private final TaskList tasks;
    private final Storage storage;
    private final Parser parser;
    private final Ui ui;

    /** The welcome logo of zener.Zenerbot! */
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

    private Zenerbot() {
        this.tasks = new TaskList();
        this.storage = new Storage("./data/zener.txt");
        this.parser = new Parser();
        this.ui = new Ui();
    }

    /**
     * Gets the singleton instance of the bot.
     * <a href="https://www.baeldung.com/java-singleton">see Java Singleton</a>
     *
     * @return the bot instance
     */
    public static Zenerbot getInstance() {
        if (BOT == null) {
            BOT = new Zenerbot();
        }
        return BOT;
    }

    /**
     * Gets tasks.
     *
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Is init boolean.
     *
     * @return the boolean
     */
    public boolean isInit() {
        return Zenerbot.INIT_MODE;
    }

    /**
     * Saves the current tasks to zener.abstractions.Storage.
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
     * Parses the raw string command into an executable zener.Command object <b>and executes it</b>.
     * Deals with exceptions caused by invalid commands or wrong usage.
     *
     * @param raw the raw command string
     * @return true if success, false otherwise
     */
    public boolean exec(String raw) {
        Command command;
        try {
            command = parser.getCommand(raw);
            System.out.println("command = " + command);
            String[] parameters = parser.getParameters(raw);
            if (!INIT_MODE || command.isScriptable()) {
                command.execute(Zenerbot.getInstance(), parameters);
                return true;
            } else {
                return false;
            }

        } catch (UnknownCommandException | InvalidTaskException e) {
            if (!INIT_MODE) {
                ui.consoleError(e.getMessage());
            }
            return false;
        }
    }

    /** Starts and runs the bot loop. */
    public void run() {
        // pre-initialisation
        Zenerbot.INIT_MODE = true;
        ui.consoleMessage("zener.Zenerbot by Isaac Goh\n");
        ui.consoleMessage("Initialising bot...\n");

        // loading from save file
        ui.consoleMessage("Loading from previous save...");
        try {
            storage.load(this.tasks);
        } catch (IOException e) {
            ui.consoleError("File was unable to be created due to an unknown reason.");
            ui.consoleError("Loading unsuccessful.\n");
        }

        // intro
        Zenerbot.INIT_MODE = false;
        ui.welcomeMessage();

        Command.LIST.execute(this, new String[]{}); // show list
        ui.divider();

        // bot loop (continues until termination)
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String cmd = scan.nextLine();
            ui.divider();
            exec(cmd);  // parses and executes
            ui.divider();
        }
    }

    /** Terminates the bot. */
    public void terminate() {
        this.save();    // just in case..

        ui.goodbyeMessage();
        System.exit(0);     // goodbye
    }
}
