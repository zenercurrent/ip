public class Benbot {

    /** Prints the lines with horizontal borders */
    private static void print(String... lines) {
        System.out.println("------------------------------");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("> ");
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
        print("Goodbye! Hope to see you again!");
    }
}
