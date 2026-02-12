import java.util.ArrayList;
import java.util.Scanner;

public class Zenerbot {
    /** The singleton instance of the bot */
    private static Zenerbot BOT;

    /** A collection of saved tasks */
    private static final ArrayList<Task> tasks = new ArrayList<>();

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

    /** Processes the command given to the bot */
    public void process(String command) {
        String instruction = command.strip().split(" ")[0];
        String parameters = command.length() > instruction.length()
                ? command.replaceFirst(instruction + " ", "")
                : "";

        switch (instruction) {
        // list: display all tasks
        case "list" -> {
            System.out.println("These are your current tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
        }

        // (un)mark: mark a task as (not) done
        case "mark", "unmark" -> {
            Task task = tasks.get(Integer.parseInt(parameters) - 1);
            if (instruction.equals("mark")) {
                System.out.println("Good work. This task is done:");
            } else {
                System.out.println("I will mark this as undone for now:");
            }
            System.out.println("\t" + task);
            task.setDone(instruction.equals("mark"));
        }

        // todo: create a new todo task
        case "todo" -> {
            if (command.strip().equals("todo")) {
                throw new InvalidTaskException();
            }

            Todo todo = new Todo(parameters);
            tasks.add(todo);
            System.out.println("I have created a new task for you:");
            System.out.println("\t" + todo);
            System.out.println("There are now " + tasks.size() + " task(s) to be done.");
        }

        // deadline: create a new deadline task
        case "deadline" -> {
            if (!parameters.contains("/by")) {
                throw new InvalidTaskException();
            }

            String name = parameters.strip().split(" /by ")[0];
            String by = parameters.strip().split(" /by ")[1];
            Deadline deadline = new Deadline(name, by);
            tasks.add(deadline);
            System.out.println("I have created a new task for you:");
            System.out.println("\t" + deadline);
            System.out.println("There are now " + tasks.size() + " task(s) to be done.");
        }

        // event: create a new deadline task
        case "event" -> {
            if (!parameters.contains("/from") || !parameters.contains("/to")) {
                throw new InvalidTaskException();
            }

            String name = parameters.strip().split(" /from | /to ")[0];
            String from = parameters.strip().split(" /from | /to ")[1];
            String to = parameters.strip().split(" /from | /to ")[2];
            Event event = new Event(name, from, to);
            tasks.add(event);
            System.out.println("I have created a new task for you:");
            System.out.println("\t" + event);
            System.out.println("There are now " + tasks.size() + " task(s) to be done.");
        }

        case "delete" -> {
            int i = Integer.parseInt(parameters);   // todo: handle possible exception here
            Task task = tasks.get(i - 1);
            tasks.remove(i - 1);
            System.out.println("Noted with thanks. I have removed the task:");
            System.out.println("\t" + task);
            System.out.println("Now you are left with " + tasks.size() + " tasks.");
        }

        // unknown command
        default -> throw new UnknownCommandException();
        }
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
            System.out.println("> ");
            String command = scan.nextLine();
            System.out.println("------------------------------");

            try {
                process(command);
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
