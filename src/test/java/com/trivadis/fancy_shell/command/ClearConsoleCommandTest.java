package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClearConsoleCommandTest extends TestBase {

    private ClearConsoleCommand command = new ClearConsoleCommand();

    @Test
    void testExecute() {
        
        // ARRANGE

        // ACT
        command.execute(shellState, List.of());

        // ASSERT
        String line = retrievePrintedOutput();

        String expected = Stream.generate(() -> System.lineSeparator())
                .limit(20)
                .collect(Collectors.joining());

        assertEquals(expected, line);
    }

    @Test
    void testExecute_withInvalidNumberOfArguments() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("unexpected"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testGeneralSettings() {
        assertEquals("clear", command.getName());
    }
}
