package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;

import java.util.List;

public class CurrentWorkingDirCommand extends Command {

    public CurrentWorkingDirCommand() {
        super("pwd");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {
        String pwd = shellState.getWorkingDirectory().getName();
        shellState.getOutputStream().println(pwd);
    }
}
