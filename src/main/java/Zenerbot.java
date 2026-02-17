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

    /** Saves data to a location in a hard disk. Overwrites existing data! */
    public void save() throws IOException {
        try (FileWriter file = new FileWriter(this.saveLocation);) {
            int i = 0;
            for (Task t : this.tasks) {
                i++;
                file.write(t.toCommandString() + "\n");
                if (t.isDone()) {
                    file.write("mark " + i + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Save file could not be opened/created due to an unknown error.");
            System.out.println("Save failed!");
        }
    }

    /**
     * Loads data from a location in the hard disk. Done automatically on when bot starts.
     *
     * @throws IOException if file cannot be created
     */
    public void load() throws IOException {
        File filePtr = new File(this.saveLocation);
        Scanner file;
        try {
            file = new Scanner(filePtr);
        } catch (FileNotFoundException e) {
            // ask user for permission to create
            System.out.println("Save file '" + this.saveLocation + "' does not exist!");
            System.out.println("Could not load tasks...");
            System.out.println("Create file in save location? (yes/no)");
            Scanner scan = new Scanner(System.in);
            if (scan.nextLine().equalsIgnoreCase("yes")) {
                if (filePtr.getParentFile() != null) {
                    filePtr.getParentFile().mkdirs();
                }
                if (filePtr.createNewFile()) {
                    System.out.println("Save file created at: '" + filePtr.getAbsolutePath() + "'.");
                } else {
                    System.out.println("File actually already exists?? This shouldn't happen...");
                }
            }
            file = new Scanner(filePtr);
        }

        int failures = 0;
        while (file.hasNextLine()) {
            String cmd = file.nextLine();
            boolean success = exec(cmd);
            if (!success) {
                System.out.println("Failed at: '" + cmd + "'");
                failures++;
            }
        }

        System.out.println("Load completed!");
        if (failures > 0) {
            System.out.println("Failed to import " + failures + " line(s).");
        }
        System.out.println("Loaded " + this.tasks.size() + " task(s) successfully.");
    }

    /** Terminates the bot. */
    public void terminate() {
        try {
            this.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Zenerbot.LOGO);
        System.out.println("Isaac Goh");
        System.exit(0);     // goodbye
    }
}
