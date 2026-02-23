package zener.tasks;

import zener.exceptions.InvalidTaskException;

/** ToDo tasks do not have any date/time attached to it */
public class Todo extends Task {
    /**
     * Instantiates a new ToDo task.
     *
     * @param name The name of the task
     */
    public Todo(String name) {
        super(name);
        if (name.strip().length() == 0) {
            throw new InvalidTaskException();
        }
    }

    @Override
    public String toCommandString() {
        return "todo " + this.getName();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
