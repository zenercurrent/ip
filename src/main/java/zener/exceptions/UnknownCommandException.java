package zener.exceptions;

/**
 * Thrown when command is not known by the bot.
 */
public class UnknownCommandException extends RuntimeException {

    public UnknownCommandException() {
        super("Unknown command! Did you spell it wrongly?");
    }
}
