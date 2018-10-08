package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.Resource;

import java.util.List;

public class MoveContentCommand extends Command {

    public MoveContentCommand() {
        super("mv");
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {

        if (arguments.size() != 2) {
            throw new CommandException("Command \"" + getName() + "\" needs source and destination as arguments!");
        }

        Directory workingDirectory = shellState.getWorkingDirectory();
        String source = arguments.get(0);
        String destination = arguments.get(1);

        Resource resource = Directory.findResource(shellState.getRootDirectory(), workingDirectory, source)
                .orElse(null);

        Directory targetDir = (Directory) Directory.findResource(shellState.getRootDirectory(), workingDirectory, destination)
                .filter(Resource::isDirectory)
                .orElseThrow(() -> new CommandException("No directory with name \"" + destination
                        + "\" in parent directory \"" + workingDirectory.getName() + "\" found!"));

        if (resource == null) {
            throw new CommandException("No entry with name \"" + source + "\" found!");
        }

        if(resource.isDirectory())
        {
            Directory dir = ((Directory)resource);
            Resource res = Directory.findResource(dir, dir, destination)
                    .orElse(null);

            if(res == null)
            {
                throw new CommandException("now you're busted!");
            }

            dir.getParent().removeResource(resource);
        }
        else
        {
            workingDirectory.removeResource(resource);
        }

        targetDir.addResource(resource);

    }
}
