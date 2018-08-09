package com.trivadis.fancy_shell.utility;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public final class FakePrintStream {

    private static PrintStream printStream;
    private static ByteArrayOutputStream outputStream;

    private FakePrintStream() {
    }

    public static PrintStream getInstance() {

        if (printStream == null) {
            outputStream = new ByteArrayOutputStream(512);
            printStream = new PrintStream(outputStream);
        }

        return printStream;
    }

    public static void reset() {
        outputStream.reset();
    }

    public static String retrievePrintedOutput() {
        return outputStream.toString(java.nio.charset.StandardCharsets.UTF_8);
    }

    public static String[] retrievePrintedLines() {
        return outputStream
                .toString(java.nio.charset.StandardCharsets.UTF_8)
                .split(System.lineSeparator());
    }
}
