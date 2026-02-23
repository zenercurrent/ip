import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Zenerbot {
    /** The singleton instance of the bot */
    private static Zenerbot BOT;

    /** Used by the bot to indicate if it is still initialising. (true if yes) */
    private static boolean INIT_MODE = true;

    /** A collection of saved tasks */
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** The location in the hard disk that tasks are saved. Can be changed with commands.  */
    private String saveLocation = "./data/zener.txt";

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

    private Zenerbot() {
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

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public boolean isInit() {
        return Zenerbot.INIT_MODE;
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
            String instruction = raw.strip().split(" ")[0];
            String parameters = raw.length() > instruction.length()
                    ? raw.replaceFirst(instruction + " ", "")
                    : "";
            command = Command.fromString(instruction);
            if (!INIT_MODE || command.isScriptable()) {
                command.execute(Zenerbot.getInstance(), parameters.split(" "));
                return true;
            } else {
                return false;
            }

        } catch (UnknownCommandException | InvalidTaskException e) {
            if (!INIT_MODE) {
                System.out.println(e);
            }
            return false;
        }
    }

    /** Starts and runs the bot loop. */
    public void run() {
        // pre-initialisation
        Zenerbot.INIT_MODE = true;
        System.out.println();
        System.out.println("Zenerbot by Isaac Goh");
        System.out.println();
        System.out.println("Initialising bot...");

        // loading from save file
        System.out.println("Loading from previous save...");
        try {
            this.load();
        } catch (IOException e) {
            System.out.println("File was unable to be created due to an unknown reason.");
            System.out.println("Loading unsuccessful.\n");
        }

        // intro
        Zenerbot.INIT_MODE = false;
        System.out.println(Zenerbot.LOGO);
        System.out.println("------------------------------");
        System.out.println("Hello there! I am ZenerBot, your personal assistant.");
        System.out.println("How can I help?");

        Command.LIST.execute(this, new String[]{}); // show list
        System.out.println("------------------------------");

        // bot loop (continues until termination)
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String cmd = scan.nextLine();
            System.out.println("------------------------------");
            exec(cmd);  // parses and executes
            System.out.println("------------------------------");
        }
    }

    /** Terminates the bot. */
    public void terminate() {
        this.save();    // just in case..

        System.out.println(Zenerbot.LOGO);
        System.out.println("Isaac Goh");
        System.exit(0);     // goodbye
    }
}
