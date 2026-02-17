/** Deadline tasks have a date/time associated to it. */
public class Deadline extends Task {
    private final String by;

    /**
     * Instantiates a new Deadline task.
     *
     * @param name The name of the task
     * @param by The datetime when the task is due (as string)
     */
    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toCommandString() {
        return "deadline " + this.getName() + " /by " + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
