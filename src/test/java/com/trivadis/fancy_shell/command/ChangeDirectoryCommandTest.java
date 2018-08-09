package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeDirectoryCommandTest extends TestBase {

    private ChangeDirectoryCommand command = new ChangeDirectoryCommand();

    @Test
    void testExecute_absolutePathWithParentAndIdempotentDirectory() {
        // ARRANGE

        // ACT
        command.execute(shellState, List.of("/../sources/./input/../"));

        // ASSERT
        Resource sourcesDirectory = shellState
                .getRootDirectory()
                .findResource("sources")
                .get();

        assertEquals(sourcesDirectory, shellState.getWorkingDirectory());
    }

    @Test
    void testExecute_fileSpecified_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("missing"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_moreThanOneArgumentSpecified_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of(".", "sources"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_nonExistingResourceSpecified_throwCommandException() {
        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("makefile"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void testExecute_relativePathWithSubDirectory() {
        // ARRANGE
        Directory inputDirectory = (Directory) shellState
                .getRootDirectory()
                .findResource("sources/input")
                .get();

        shellState.setWorkingDirectory(inputDirectory);

        // ACT
        command.execute(shellState, List.of("./../../../sources/input/../.."));

        // ASSERT
        assertEquals(shellState.getRootDirectory(), shellState.getWorkingDirectory());
    }

    @Test
    void testGeneralSettings() {
        assertEquals("cd", command.getName());
    }
}