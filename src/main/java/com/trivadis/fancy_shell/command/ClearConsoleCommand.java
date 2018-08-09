package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;

import java.util.List;

public class ClearConsoleCommand extends Command {

    public ClearConsoleCommand() {
        super("clear");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {

        if (arguments.size() != 0) {
            throw new CommandException("Command \"" + getName() + "\" does not accept any arguments!");
        }

        for (int i = 0; i < 20; i++) {
            shellState.getOutputStream().print(System.lineSeparator());
        }
    }
}
