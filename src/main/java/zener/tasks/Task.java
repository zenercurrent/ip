package zener.tasks;

import java.time.format.DateTimeFormatter;

/**
 * A task created by the user.
 * Can be marked as done.
 */
public abstract class Task {
    private String name;
    private boolean isDone;

    /** Formatter for all tasks with date time displays */
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM ''yy HH:mm");
    protected final DateTimeFormatter formatterCmd = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Instantiates a new Task.
     * The task is set as undone initially.
     *
     * @param name The name of the task
     */
    public Task(String name) {
        this.name = name;
        isDone = false;
    }

    /**
     * Gets the string command used to create this task.
     *
     * @return the string command
     */
    public abstract String toCommandString();

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }
}
