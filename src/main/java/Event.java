/** Event tasks start at a specific datetime and ends at a specific date/time. */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Instantiates a new Event task.
     *
     * @param name The name of the task
     * @param from The datetime when the event starts
     * @param to The datetime when the event ends
     */
    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
