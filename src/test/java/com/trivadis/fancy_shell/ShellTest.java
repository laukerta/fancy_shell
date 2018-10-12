package com.trivadis.fancy_shell;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ShellTest extends TestBase {

    private Shell shell = new Shell(shellState, Shell.COMMANDS);

    @Test
    void testRun_assertMultipleCommandsWork() throws IOException {
        // ARRANGE
        when(shellState.getInputStreamReader().readLine())
                .thenReturn("cd sources", "ls", "exit");

        // ACT
        shell.run();

        // ASSERT
        assertTrue(shellState.isTerminate());

        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("user@host />user@host sources>. .. main.c input", lines[0]);
        assertEquals("user@host sources>", lines[1]);
    }

    @Test
    void testRun_assertChangeDirAnd_printCurrentDir() throws IOException {
        // ARRANGE
        when(shellState.getInputStreamReader().readLine())
                .thenReturn("cd sources", "pwd", "exit");

        // ACT
        shell.run();

        // ASSERT
        assertTrue(shellState.isTerminate());

        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("user@host />user@host sources>/sources", lines[0]);
        assertEquals("user@host sources>", lines[1]);
    }

    @Test
    void testRun_exitShell() throws IOException {
        // ARRANGE
        when(shellState.getInputStreamReader().readLine()).thenReturn("exit");

        // ACT
        shell.run();

        // ASSERT
        assertTrue(shellState.isTerminate());

        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);
        assertEquals("user@host />", lines[0]);
    }

    @Test
    void testRun_noCommandSpecified() throws IOException {
        // ARRANGE
        when(shellState.getInputStreamReader().readLine()).thenReturn("", "exit");

        // ACT
        shell.run();

        // ASSERT
        assertTrue(shellState.isTerminate());

        String[] lines = retrievePrintedLines();

        assertEquals(1, lines.length);
        assertEquals("user@host />user@host />", lines[0]);
    }

    @Test
    void testRun_unknownCommandSpecified() throws IOException {
        // ARRANGE
        when(shellState.getInputStreamReader().readLine()).thenReturn("unknown", "exit");

        // ACT
        shell.run();

        // ASSERT
        assertTrue(shellState.isTerminate());

        String[] lines = retrievePrintedLines();

        assertEquals(2, lines.length);
        assertEquals("user@host />Unknown command \"unknown\"!", lines[0]);
    }

}
