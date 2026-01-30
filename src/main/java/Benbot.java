import java.util.ArrayList;
import java.util.Scanner;

public class Benbot {
    /** A collection of saved tasks */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /** Flag that allows the bot to terminate */
    private static boolean canExit = false;


    /** Prints the lines with horizontal borders */
    private static void print(String... lines) {
        System.out.println("------------------------------");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("> ");
    }

    /** Processes the command given to the bot */
    private static void process(String command) {
        String instruction = command.strip().split(" ")[0];
        String parameters = command.replaceFirst(instruction + " ", "");

        switch (instruction) {
        // bye: exit program
        case "bye":
            print("Goodbye! It was a nice chat! :)");
            canExit = true;
            break;

        // list: display all tasks
        case "list":
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
            break;

        // (un)mark: mark a task as (not) done
        case "mark":
        case "unmark":
            Task task = tasks.get(Integer.parseInt(parameters));
            task.setDone(instruction.equals("mark"));
            break;

        // add as new task
        default:
            tasks.add(new Task(command));
            print("added: " + command);
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
        print("Hello there! I am Benbot, your personal assistant.", "How can I help?");

        while (!canExit) {
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            process(command);
        }
    }
}
