package com.trivadis.fancy_shell;

import com.trivadis.fancy_shell.resource.Directory;

import java.io.BufferedReader;
import java.io.PrintStream;

public class ShellState {

    private Directory rootDirectory;
    private Directory workingDirectory;
    private boolean terminate;
    private PrintStream outputStream;
    private BufferedReader inputStreamReader;

    public ShellState(PrintStream outputStream, BufferedReader inputStreamReader, Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.workingDirectory = rootDirectory;
        this.terminate = false;
        this.outputStream = outputStream;
        this.inputStreamReader = inputStreamReader;
    }

    public BufferedReader getInputStreamReader() {
        return inputStreamReader;
    }

    public PrintStream getOutputStream() {
        return outputStream;
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public Directory getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(Directory workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }
}
