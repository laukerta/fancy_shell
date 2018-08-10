package com.trivadis.fancy_shell;

import com.trivadis.fancy_shell.utility.ExampleFileStructure;
import com.trivadis.fancy_shell.utility.FakePrintStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.BufferedReader;

public class TestBase {

    //test comment
    protected static ShellState shellState;
    private static BufferedReader inputStreamMock;

    @BeforeAll
    static void setup() {
        inputStreamMock = Mockito.mock(BufferedReader.class);
        shellState = new ShellState(FakePrintStream.getInstance(), inputStreamMock, ExampleFileStructure.getRoot());
    }

    @BeforeEach
    void reset() {
        FakePrintStream.reset();
        Mockito.reset(inputStreamMock);
        shellState.setWorkingDirectory(ExampleFileStructure.getRoot());
        shellState.setTerminate(false);
    }

    protected String retrievePrintedOutput() {
        return FakePrintStream.retrievePrintedOutput();
    }

    protected String[] retrievePrintedLines() {
        return FakePrintStream.retrievePrintedLines();
    }
}
