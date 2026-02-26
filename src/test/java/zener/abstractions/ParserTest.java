package zener.abstractions;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import zener.Command;
import zener.exceptions.UnknownCommandException;

public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    public void getCommand_validCommandWithExtraSpaces_parsesCorrectly() {
        assertEquals(Command.LIST, parser.getCommand("   list   "));
    }

    @Test
    public void getCommand_invalidCommand_throwsUnknownCommand() {
        assertThrows(UnknownCommandException.class, () -> parser.getCommand("nonsense"));
    }

    @Test
    public void getParameters_commandOnly_returnsEmptyArray_expectedBehavior() {
        String[] params = parser.getParameters("list");
        assertArrayEquals(new String[0], params);
    }

    @Test
    public void getParameters_multipleSpaces_betweenWords_expectedBehavior() {
        String[] params = parser.getParameters("todo   read   book");
        assertArrayEquals(new String[]{"read", "book"}, params);
    }
}
