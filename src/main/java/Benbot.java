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
        if (command.equals("bye")) {
            // bye: exit program
            print("Goodbye! It was a nice chat! :)");
            canExit = true;
        } else if (command.equals("list")) {
            // list: display all tasks
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
        } else {
            // add as new task
            tasks.add(new Task(command));
            print("added: " + command);
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
