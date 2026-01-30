/**
 * A task created by the user.
 * Can be marked as done.
 */
public class Task {
    private String name;

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

    private boolean isDone;

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

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }
}
