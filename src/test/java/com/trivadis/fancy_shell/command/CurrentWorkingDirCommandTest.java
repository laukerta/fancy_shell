package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;
import com.trivadis.fancy_shell.utility.ExampleFileStructure;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentWorkingDirCommandTest extends TestBase {

    private CurrentWorkingDirCommand command = new CurrentWorkingDirCommand();

    @Test
    void test_showCurrentWorkingDir_shellAtRootDir() {
        // ACT
        command.execute(shellState, Collections.emptyList());

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);//???? 1 line without any implementation
        assertEquals(ExampleFileStructure.getRoot().getName(), lines[0]);
    }

    @Test
    void test_showCurrentWorkingDir_shellAtSubdir() {
        // ARRANGE
        Directory sourcesDir = ExampleFileStructure.getRoot().getResources().stream()
                .filter(it -> "sources".equals(it.getName()))
                .filter(Resource::isDirectory)
                .map(Directory.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Test data is wrong"));
        shellState.setWorkingDirectory(sourcesDir);

        // ACT
        command.execute(shellState, Collections.emptyList());

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);//???? 1 line without any implementation
        assertEquals("/sources", lines[0]);
    }

    @Test
    void testGeneralSettings() {
        assertEquals("pwd", command.getName());
    }
}