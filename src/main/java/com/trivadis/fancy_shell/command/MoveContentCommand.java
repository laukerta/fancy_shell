package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.File;
import com.trivadis.fancy_shell.resource.Resource;

import java.util.List;
import java.util.Optional;

public class MoveContentCommand extends Command {

    public MoveContentCommand() {
        super("mv");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {

        if (arguments.size() != 2) {
            throw new CommandException("Command \"" + getName() + "\" needs two arguments: source and destination!");
        }

        Directory workingDirectory = shellState.getWorkingDirectory();

        File sourceFile = new File(arguments.get(0));
        File destFile = new File(arguments.get(1));

        Resource srcRes = Directory.findResource(shellState.getRootDirectory(), workingDirectory, arguments.get(0))
                .orElse(null);

        if (srcRes == null) {
            throw new CommandException("No entry with name \"" + arguments.get(0) + "\" found!");
        }

        Directory targetDir = (Directory) Directory.findResource(shellState.getRootDirectory(), workingDirectory, arguments.get(1))
                .filter(Resource::isDirectory)
                .orElseThrow(() -> new CommandException("No directory with name \"" + arguments.get(1)
                        + "\" in parent directory \"" + workingDirectory.getName() + "\" found!"));


        if (srcRes.isDirectory()) {
            Directory myDir = (Directory) Directory.findResource(shellState.getRootDirectory(), (Directory)srcRes, targetDir.getName())
                    .filter(Resource::isDirectory)
                    .orElse(null);
            if (myDir != null) {
                throw new CommandException("Moving an source folder to one of its children is not allowed!");
            }
        }

        targetDir.addResource(srcRes);
        if (srcRes.isDirectory()) {
            //TODO
            ((Directory)srcRes).getParent().removeResource(srcRes);
        }
        else {
            workingDirectory.removeResource(srcRes);
        }
    }
}