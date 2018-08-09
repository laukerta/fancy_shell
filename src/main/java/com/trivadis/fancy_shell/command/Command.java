package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;

import java.util.List;

public abstract class Command {

    private String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(ShellState shellState, List<String> arguments);
}
