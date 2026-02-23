package zener.tasks;

import org.junit.jupiter.api.Test;
import zener.exceptions.InvalidTaskException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    public void dateOnly_defaultsTo2359() {
        Deadline d = new Deadline("submit report", "23/2/2025");
        assertEquals(LocalDate.of(2025, 2, 23), d.getBy().toLocalDate());
        assertEquals(LocalTime.of(23, 59), d.getBy().toLocalTime());
    }

    @Test
    public void timeOnly_defaultsToToday() {
        Deadline d = new Deadline("meeting", "1045");
        assertEquals(LocalDate.now(), d.getBy().toLocalDate());
        assertEquals(LocalTime.of(10, 45), d.getBy().toLocalTime());
    }

    @Test
    public void dateAndTime_parsesBoth() {
        Deadline d = new Deadline("meeting", "23/2/2025 1045");
        assertEquals(LocalDate.of(2025, 2, 23), d.getBy().toLocalDate());
        assertEquals(LocalTime.of(10, 45), d.getBy().toLocalTime());
    }

    @Test
    public void invalidInput_defaultsToToday2359() {
        Deadline d = new Deadline("bad", "invalid-input");
        assertEquals(LocalDate.now(), d.getBy().toLocalDate());
        assertEquals(LocalTime.of(23, 59), d.getBy().toLocalTime());
    }

    @Test
    public void emptyName_throwsError() {
        assertThrows(InvalidTaskException.class, () -> new Deadline("", "1045"));
    }
}