import java.util.ArrayList;

/**
 * Containing tasks currently stored by the bot.
 */
public class TaskList extends ArrayList<Task> {

    /**
     * Instantiates a new empty task list.
     */
    public TaskList() {

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
