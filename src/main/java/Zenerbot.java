import java.util.ArrayList;
import java.util.Scanner;

public class Zenerbot {
    /** The singleton instance of the bot */
    private static Zenerbot BOT;

    /** A collection of saved tasks */
    private final ArrayList<Task> tasks = new ArrayList<>();

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

    /** Starts and runs the bot loop. */
    public void run() {
        System.out.println(Zenerbot.LOGO);
        System.out.println("------------------------------");
        System.out.println("Hello there! I am ZenerBot, your personal assistant.");
        System.out.println("How can I help?");
        System.out.println("------------------------------");

        Scanner scan = new Scanner(System.in);
        // bot loop (continues until termination)
        while (scan.hasNextLine()) {
            String cmd = scan.nextLine();
            System.out.println("------------------------------");

            try {
//                process(command);
                String instruction = cmd.strip().split(" ")[0];
                String parameters = cmd.length() > instruction.length()
                        ? cmd.replaceFirst(instruction + " ", "")
                        : "";
                Command command = Command.fromString(instruction);
                command.execute(Zenerbot.getInstance(), parameters.split(" "));

            } catch (UnknownCommandException | InvalidTaskException e) {
                System.out.println(e);
            }
            System.out.println("------------------------------");
        }
    }

    /** Terminates the bot. */
    public void terminate() {
        System.out.println(Zenerbot.LOGO);
        System.out.println("Isaac Goh");
        System.exit(0);     // goodbye
    }
}
