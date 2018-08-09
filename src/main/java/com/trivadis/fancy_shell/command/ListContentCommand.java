package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;

import java.util.List;

public class ListContentCommand extends Command {

    public ListContentCommand() {
        super("ls");
    }

    public void execute(ShellState shellState, List<String> arguments) {

        Directory workingDirectory = shellState.getWorkingDirectory();

        if (arguments.isEmpty()) {
            shellState.getOutputStream().println(workingDirectory.printContent());
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String argument : arguments) {

            Resource resource = Directory.findResource(shellState.getRootDirectory(), workingDirectory, argument)
                    .orElse(null);

            if (resource == null) {
                throw new CommandException("No entry with name \"" + argument + "\" found!");
            }

            if (resource.isDirectory()) {
                Directory directory = (Directory) resource;

                builder
                        .append("Contents of directory \"" + resource.getName() + "\":")
                        .append(System.lineSeparator())
                        .append("    " + directory.printContent())
                        .append(System.lineSeparator());
            } else {
                builder.append("File: ").append(resource.printContent()).append(System.lineSeparator());
            }
        }

        shellState.getOutputStream().print(builder.toString());
    }
}
