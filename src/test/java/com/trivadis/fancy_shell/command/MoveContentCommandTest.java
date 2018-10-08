package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveContentCommandTest extends TestBase {

    private MoveContentCommand command = new MoveContentCommand();

    @Test
    void testExecute_withInvalidNumberOfArguments() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("onlyOne"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_nonExistingResourceSpecified_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("./a.out", "./missing"));

        // ACT
        assertThrows(CommandException.class, executableCommand);
    }

    @Test
    void testExecute_absoluteSourceToAbsoluteDest() {
        // ACT
        command.execute(shellState, List.of("./a.out", "./sources/input"));

        // ARRANGE
        Directory inputDirectory = (Directory) shellState
                .getRootDirectory()
                .findResource("sources/input")
                .get();

        shellState.setWorkingDirectory(inputDirectory);

        assertTrue(shellState.getWorkingDirectory().getResources().contains("a.out"));
    }

    @Test
    void testExecute_relativeSourceToRelativeDest() {
        // ACT
        command.execute(shellState, List.of("./../../../sources/input/../../a.out", "./../../../sources/input"));

        // ARRANGE
        Directory inputDirectory = (Directory) shellState
                .getRootDirectory()
                .findResource("sources/input")
                .get();

        shellState.setWorkingDirectory(inputDirectory);

        assertTrue(shellState.getWorkingDirectory().getResources().contains("a.out"));
    }

    @Test
    void testExecute_sourceFolderToChild_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("./sources", "./sources/input"));

        // ACT
        assertThrows(CommandException.class, executableCommand);
    }

    @Test
    void testGeneralSettings() {
        assertEquals("mv", command.getName());
    }

}
