package zener.exceptions;

/**
 * Thrown when the task is invalid or is missing required parameters.
 */
public class InvalidTaskException extends RuntimeException {

    /**
     * Instantiates a new invalid task exception.
     */
    public InvalidTaskException() {
        super("""
                Task was not setup properly! Ensure the parameters are correct!\s
                Valid task commands:\s
                \ttodo <name>\s
                \tdeadline <name> /by <datetime>\s
                \tevent <name> /from <datetime> /to <datetime>""");
    }
}
