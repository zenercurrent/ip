/**
 * Thrown when command is not known by the bot.
 */
public class UnknownCommandException extends RuntimeException {
    private final String message;
    public UnknownCommandException() {
        this.message = "Unknown command! Did you spell it wrongly?";
    }

    @Override
    public String toString() {
        return this.message;
    }
}
