package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;

import java.util.List;

public class ChangeDirectoryCommand extends Command {

    public ChangeDirectoryCommand() {
        super("cd");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {

        if (arguments.size() != 1) {
            throw new CommandException("Command \"" + getName() + "\" needs directory name as single argument!");
        }

        Directory workingDirectory = shellState.getWorkingDirectory();
        String directoryName = arguments.get(0);

        Directory directory = (Directory) Directory.findResource(shellState.getRootDirectory(), workingDirectory, directoryName)
                .filter(Resource::isDirectory)
                .orElseThrow(() -> new CommandException("No directory with name \"" + directoryName
                        + "\" in parent directory \"" + workingDirectory.getName() + "\" found!"));

        shellState.setWorkingDirectory(directory);
    }
}
