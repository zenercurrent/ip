package zener.abstractions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import zener.tasks.Deadline;
import zener.tasks.TaskList;
import zener.tasks.Todo;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void save_writesCommandsAndMarkLinesInOrder() throws Exception {
        Path saveFile = tempDir.resolve("zener_test.txt");
        Storage storage = new Storage(saveFile.toString());

        TaskList tasks = new TaskList();
        Todo t1 = new Todo("read book");
        Deadline d2 = new Deadline("submit report", "23/2/2025 1045");
        d2.setDone(true); // should produce "mark 2"

        tasks.add(t1);
        tasks.add(d2);

        storage.save(tasks);

        List<String> lines = Files.readAllLines(saveFile);

        // Expected:
        // 1) todo read book
        // 2) deadline submit report /by ...
        // 3) mark 2
        assertEquals("todo read book", lines.get(0));
        assertTrue(lines.get(1).startsWith("deadline submit report /by "), "Expected deadline command line");
        assertEquals("mark 2", lines.get(2), "Expected mark line to reference 2nd task");

        assertEquals(3, lines.size(), "Expected exactly 3 lines written");
    }

    @Test
    public void save_noDoneTasks_noMarkLines() throws Exception {
        Path saveFile = tempDir.resolve("zener_test2.txt");
        Storage storage = new Storage(saveFile.toString());

        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("wash dishes"));

        storage.save(tasks);

        List<String> lines = Files.readAllLines(saveFile);

        assertEquals(List.of("todo read book", "todo wash dishes"), lines);
    }
}
