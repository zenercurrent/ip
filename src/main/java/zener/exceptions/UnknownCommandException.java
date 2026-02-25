package zener.exceptions;

/**
 * Thrown when command is not known by the bot.
 */
public class UnknownCommandException extends RuntimeException {

    /**
     * Instantiates a new unknown command exception.
     */
    public UnknownCommandException() {
        super("Unknown command! Did you spell it wrongly?");
    }
}
