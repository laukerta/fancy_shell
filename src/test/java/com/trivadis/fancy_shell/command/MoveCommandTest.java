package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest extends TestBase {

    private Command command = new MoveCommand();

    @Test
    void execute_moveDirectoryToSubdirectory_assertCommandException() {

        // ARRANGE
        Executable executableCommand = () -> command.execute(shellState, List.of("/sources", "./sources/input"));

        // ACT
        assertThrows(CommandException.class, executableCommand);

        // ASSERT
    }

    @Test
    void execute_moveSubdirectoryToParent_assertCommandException() {

        // ARRANGE

        // ACT
        command.execute(shellState, List.of("sources/input", "/"));

        // ASSERT
        Directory rootDirectory = shellState.getRootDirectory();

        Directory inputDirectory = (Directory) rootDirectory
                .findResource("input")
                .orElse(null);

        assertNotNull(inputDirectory);
        assertEquals(4, inputDirectory.getResources().size());
        assertEquals(rootDirectory, inputDirectory.findResource("..").orElse(null));
        assertEquals(rootDirectory, inputDirectory.getParent());

        Directory sourcesDirectory = (Directory) rootDirectory.findResource("sources")
                .filter(Resource::isDirectory)
                .orElse(null);

        assertEquals(3, sourcesDirectory.getResources().size());
        assertNull(sourcesDirectory.findResource("input").orElse(null));
    }
}