package zener.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import zener.exceptions.InvalidTaskException;

public class TodoTest {

    @Test
    public void constructor_emptyName_throwsInvalidTaskException() {
        assertThrows(InvalidTaskException.class, () -> new Todo(""));
        assertThrows(InvalidTaskException.class, () -> new Todo("   "));
    }

    @Test
    public void toCommandString_validTodo_correctFormat() {
        Todo t = new Todo("read book");
        assertEquals("todo read book", t.toCommandString());
    }

    @Test
    public void setDone_true_toStringShowsX() {
        Todo t = new Todo("read book");
        t.setDone(true);
        assertTrue(t.toString().contains("[X]"), "Expected done tasks to contain [X]");
    }

    @Test
    public void setDone_false_toStringShowsBlank() {
        Todo t = new Todo("read book");
        t.setDone(false);
        assertTrue(t.toString().contains("[ ]"), "Expected undone tasks to contain [ ]");
    }
}
