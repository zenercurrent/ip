package zener.abstractions;

import zener.Command;
import zener.exceptions.InvalidTaskException;
import zener.exceptions.UnknownCommandException;

/**
 * Parses and understands the user input, and converts it to commands and parameters.
 * <p>
 * Note: the implementation here is a bit trivial because most of the heavy lifting is done within the Command enum.
 */
public class Parser {
    /**
     * Based on the raw input string, attempts to get the user command (first word).
     *
     * @param raw the raw input string
     * @return the output Command, null if fail
     */
    public Command getCommand(String raw) throws UnknownCommandException, InvalidTaskException {
        assert raw != null : "Input must not be null.";
        raw = raw.trim();
        assert !raw.isEmpty() : "Command cannot be empty.";

        String instruction = raw.strip().split("\\s+")[0];
        return Command.fromString(instruction);
    }

    /**
     * Based on the input string, retrieves the parameters for the command (after the first word).
     * This function does not care about parameter correctness; that is handled by the Command enum.
     *
     * @param raw the raw input string
     * @return the parsed parameters
     */
    public String[] getParameters(String raw) {
        if (raw == null) {
            return new String[0];
        }
        raw = raw.trim();
        if (raw.isEmpty()) {
            return new String[0];
        }

        String[] parts = raw.strip().split("\\s+", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            return new String[0];
        }

        return parts[1].trim().split("\\s+");
    }
}
