package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveContentCommandTest extends TestBase
{

    private ListContentCommand ls = new ListContentCommand();
    private MoveContentCommand mv = new MoveContentCommand();

    @Test
    void testExecute_copyFileToDirectory()
    {
        // ARRANGE

        // ACT
        mv.execute(shellState, List.of("a.out", "sources"));
        ls.execute(shellState, List.of("sources"));

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("Contents of directory \"sources\":", lines[0]);
        assertEquals("    . .. main.c input a.out", lines[1]);
    }

    @Test
    void testExecute_copyDirectoryToDirectory()
    {
        // ARRANGE

        // ACT
        mv.execute(shellState, List.of("sources/input", "/"));
        ls.execute(shellState, List.of("/"));

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("Contents of directory \"/\":", lines[0]);
        assertEquals("    . .. makefile sources input", lines[1]);
    }

    @Test
    void testExecute_wrongArguments_throwCommandException()
    {
        // ARRANGE
        Executable executableCommand = () -> mv.execute(shellState, List.of("missing"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_wrongSourceArgument_throwCommandException()
    {
        // ARRANGE
        Executable executableCommand = () -> mv.execute(shellState, List.of("missing", "sources"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_wrongDestinationArgument_throwCommandException()
    {
        // ARRANGE
        Executable executableCommand = () -> mv.execute(shellState, List.of("a.out", "missing"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_copyContentIntoItself_throwCommandException()
    {
        // ARRANGE
        Executable executableCommand = () -> mv.execute(shellState, List.of("sources", "sources/input"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

}
