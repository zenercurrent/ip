package zener;

import org.junit.jupiter.api.Test;
import zener.exceptions.UnknownCommandException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandTest {

    @Test
    public void fromString_validLowercase() {
        assertEquals(Command.TODO, Command.fromString("todo"));
    }

    @Test
    public void fromString_validMixedCase() {
        assertEquals(Command.LIST, Command.fromString("LiSt"));
    }

    @Test
    public void fromString_invalidCommand() {
        assertThrows(UnknownCommandException.class, () -> Command.fromString("asjkldhfasdkfjh"));
    }
}