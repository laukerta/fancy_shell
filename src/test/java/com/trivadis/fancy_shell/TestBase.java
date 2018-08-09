package com.trivadis.fancy_shell;

import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.utility.ExampleFileStructure;
import com.trivadis.fancy_shell.utility.FakePrintStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.BufferedReader;

public class TestBase {

    protected static ShellState shellState;
    private static BufferedReader inputStreamMock;

    @BeforeAll
    static void setup() {
        inputStreamMock = Mockito.mock(BufferedReader.class);
        shellState = new ShellState(FakePrintStream.getInstance(), inputStreamMock, null);
    }

    @BeforeEach
    void reset() {
        FakePrintStream.reset();
        Mockito.reset(inputStreamMock);

        Directory rootDirectory = ExampleFileStructure.build();
        shellState.setRootDirectory(rootDirectory);
        shellState.setWorkingDirectory(rootDirectory);
        shellState.setTerminate(false);
    }

    protected String[] retrievePrintedLines() {
        return FakePrintStream.retrievePrintedLines();
    }

    protected String retrievePrintedOutput() {
        return FakePrintStream.retrievePrintedOutput();
    }
}
