package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.TestBase;
import com.trivadis.fancy_shell.utility.ExampleFileStructure;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentWorkingDirCommandTest extends TestBase {

    private CurrentWorkingDirCommand command = new CurrentWorkingDirCommand();

    @Test
    void test_showCurrentWorkingDir() {
        // ACT
        command.execute(shellState, Collections.emptyList());

        // ASSERT
        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);//???? 1 line without any implementation
        assertEquals(ExampleFileStructure.getRoot().getName(), lines[0]);
    }

    @Test
    void testGeneralSettings() {
        assertEquals("pwd", command.getName());
    }
}