import java.util.Scanner;

public class Benbot {
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
        // bye: exit program
        if (command.equals("bye")) {
            print("Goodbye! It was a nice chat! :)");
            canExit = true;
        } else {
            print(command);
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
