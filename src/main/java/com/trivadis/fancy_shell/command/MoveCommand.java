package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;

import java.util.List;
import java.util.stream.Stream;

public class MoveCommand extends Command {

    public MoveCommand() {
        super("mv");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {

        if (arguments.size() != 2) {
            throw new CommandException("source and destination resources must be provided!");
        }

        String sourcePath = arguments.get(0);
        String destinationPath = arguments.get(1);

        Directory rootDirectory = shellState.getRootDirectory();
        Directory workingDirectory = shellState.getWorkingDirectory();

        Resource source = Directory.findResource(rootDirectory, workingDirectory, sourcePath)
                .orElse(null);

        if (source == null) {
            throw new CommandException("Not resource on path " + sourcePath + " found!");
        }

        Directory destination = (Directory) Directory.findResource(rootDirectory, workingDirectory, destinationPath)
                .filter(Resource::isDirectory)
                .orElse(null);

        if (destination == null) {
            throw new CommandException("Not directory on path " + destinationPath + " found!");
        }

        boolean isSourceParentOfDestination = Stream.iterate(
                destination,
                dir -> dir.getParent() != dir,
                dir -> dir.getParent())
                .filter(dir -> dir.equals(source))
                .map(dir -> true)
                .findFirst()
                .orElse(false);

        if (isSourceParentOfDestination) {
            throw new CommandException("Source mustn't be destination or its parent!");
        }

        destination.addResource(source);
    }
}
