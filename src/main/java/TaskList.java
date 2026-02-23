import java.util.ArrayList;
import java.util.List;

/**
 * Containing tasks currently stored by the bot. Can be initialised with an array of tasks.
 */
public class TaskList extends ArrayList<Task> {
    private final ArrayList<Task> tasks;

    /**
     * Instantiates a new task list with an initial array of tasks.
     *
     * @param tasks the initial tasks
     */
    public TaskList(Task[] tasks) {
        this();
        this.tasks.addAll(List.of(tasks));
    }

    /**
     * Instantiates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public Task get(int index) {
        return super.get(index);
    }

    @Override
    public boolean add(Task task) {
        return super.add(task);
    }

    @Override
    public Task remove(int index) {
        return super.remove(index);
    }
}
