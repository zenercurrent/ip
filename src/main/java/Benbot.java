import java.util.ArrayList;
import java.util.Scanner;

public class Benbot {
    /** A collection of saved tasks */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /** Flag that allows the bot to terminate */
    private static boolean canExit = false;

    /** Processes the command given to the bot */
    private static void process(String command) {
        String instruction = command.strip().split(" ")[0];
        String parameters = command.replaceFirst(instruction + " ", "");

        switch (instruction) {
        // bye: exit program
        case "bye":
            System.out.println("Goodbye! It was a nice chat! :)");
            canExit = true;
            break;

        // list: display all tasks
        case "list":
            System.out.println("These are your current tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
            break;

        // (un)mark: mark a task as (not) done
        case "mark":
        case "unmark":
            Task task = tasks.get(Integer.parseInt(parameters) - 1);

            if (instruction.equals("mark")) {
                System.out.println("Good work. This task is done:");
            } else {
                System.out.println("I will mark this as undone for now:");
            }
            System.out.println("\t" + task);
            task.setDone(instruction.equals("mark"));
            break;

        // add as new task
        default:
            System.out.println("New task: " + command);
            tasks.add(new Task(command));
            break;
        }
    }

    public static void main(String[] args) {
        String logo = """
                ▄▄                ▄▄               \s
                ██                ██           ██  \s
                ████▄ ▄█▀█▄ ████▄ ████▄ ▄███▄ ▀██▀▀\s
                ██ ██ ██▄█▀ ██ ██ ██ ██ ██ ██  ██  \s
                ████▀ ▀█▄▄▄ ██ ██ ████▀ ▀███▀  ██  \s
                """;
        System.out.println(logo);
        System.out.println("------------------------------");
        System.out.println("Hello there! I am Benbot, your personal assistant.");
        System.out.println("How can I help?");
        System.out.println("------------------------------");

        while (!canExit) {
            System.out.println("> ");
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            System.out.println("------------------------------");
            process(command);
            System.out.println("------------------------------");
        }
        System.out.println(logo);
        System.out.println("Isaac Goh");
    }
}
