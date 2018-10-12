package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.resource.Directory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentWorkingDirCommand extends Command {

    public CurrentWorkingDirCommand() {
        super("pwd");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {
        Directory currentDir = shellState.getWorkingDirectory();
        Directory rootDirectory = shellState.getRootDirectory();
        if (rootDirectory == currentDir) {
            shellState.getOutputStream().println(currentDir.getName());
            return;
        }

        List<String> paths = new ArrayList<>();
        while (currentDir != rootDirectory) {
            paths.add(currentDir.getName());
            currentDir = currentDir.getParent();
        }

        Collections.reverse(paths);

        shellState.getOutputStream().print(rootDirectory.getName());
        shellState.getOutputStream().println(String.join("/", paths));
    }
}
