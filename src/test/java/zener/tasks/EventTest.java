package zener.tasks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import zener.exceptions.InvalidTaskException;

public class EventTest {

    @Test
    public void constructor_emptyName_throwsInvalidTaskException() {
        assertThrows(InvalidTaskException.class, () -> new Event("", "1/1/2025 0900", "1/1/2025 1000"));
    }

    @Test
    public void toCommandString_fromAndToDatesPreserved_expectedBehavior() {
        // This test will FAIL on your current code because Event sets dateTo from parsedFrom.
        // (dateTo = LocalDate.from(parsedFrom) instead of parsedTo)
        Event e = new Event("meeting", "1/1/2025 0900", "2/1/2025 1000");
        String cmd = e.toCommandString();

        assertTrue(cmd.contains("/from 1/1/2025 0900"), "Expected /from to keep 1/1/2025 0900");
        assertTrue(cmd.contains("/to 2/1/2025 1000"), "Expected /to to keep 2/1/2025 1000");
    }

    @Test
    public void constructor_toBeforeFrom_autoSwaps_expectedBehavior() {
        Event e = new Event("meeting", "2/1/2025 1000", "1/1/2025 0900");
        String cmd = e.toCommandString();

        assertTrue(cmd.contains("/from 1/1/2025 0900"), "Expected /from to be earlier after swap");
        assertTrue(cmd.contains("/to 2/1/2025 1000"), "Expected /to to be later after swap");
    }
}
