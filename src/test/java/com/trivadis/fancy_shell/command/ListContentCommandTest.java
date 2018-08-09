package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListContentCommandTest extends TestBase {

    private ListContentCommand command = new ListContentCommand();

    @Test
    void testExecute_absolutePathWithParentAndIdempotentDirectory() {
        // ARRANGE

        // ACT
        command.execute(shellState, List.of("/sources/input/../."));

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("Contents of directory \"sources\":", lines[0]);
        assertEquals("    . .. main.c input", lines[1]);
    }

    @Test
    void testExecute_nonExistingResourceSpecified_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("missing"));

        // ACT
        assertThrows(CommandException.class, executableCommand);
    }

    @Test
    void testExecute_relativePathWithRootDirectoryAndMultipleArguments_printFileAndDirectory() {
        // ARRANGE

        // ACT
        command.execute(shellState, List.of("a.out", "./makefile", ".."));

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(4, lines.length);
        assertEquals("File: a.out", lines[0]);
        assertEquals("File: makefile", lines[1]);
        assertEquals("Contents of directory \"/\":", lines[2]);
        assertEquals("    . .. a.out makefile sources", lines[3]);
    }

    @Test
    void testExecute_relativePathWithSubDirectoryAndNoArguments_printWorkingDirectory() {
        // ARRANGE
        Directory inputDirectory = (Directory) shellState
                .getRootDirectory()
                .findResource("sources/input")
                .get();

        shellState.setWorkingDirectory(inputDirectory);

        // ACT
        command.execute(shellState, List.of());

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);
        assertEquals(". .. gamepad.c gamepad.h", lines[0]);
    }

    @Test
    void testGeneralSettings() {
        assertEquals("ls", command.getName());
    }
}