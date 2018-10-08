package com.trivadis.fancy_shell;

import com.trivadis.fancy_shell.command.*;
import com.trivadis.fancy_shell.exception.CommandException;
import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Shell {

    public static List<Command> COMMANDS = List.of(
            new VirtualCommand("exit", (shellState, arguments) -> shellState.setTerminate(true)),
            new ListContentCommand(),
            new ChangeDirectoryCommand(),
            new ClearConsoleCommand(),
            new MoveContentCommand()
    );

    private ShellState state;
    private Map<String, Command> commandsByName;

    public Shell(ShellState state, List<Command> commands) {
        this.state = state;
        commandsByName = commands.stream().collect(Collectors.toMap(Command::getName, Function.identity()));
    }

    public static void main(String[] args) {

        Directory root = new Directory("/");

        root.addResource(new File("a.out"));
        root.addResource(new File("makefile"));

        Directory sourcesDirectory = new Directory("sources");
        sourcesDirectory.addResource(new File("main.c"));

        Directory inputDirectory = new Directory("input");
        inputDirectory.addResource(new File("gamepad.c"));
        inputDirectory.addResource(new File("gamepad.h"));

        sourcesDirectory.addResource(inputDirectory);

        root.addResource(sourcesDirectory);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Shell shell = new Shell(new ShellState(System.out, bufferedReader, root), COMMANDS);

        shell.run();
    }

    private void acceptCommand() {

        state.getOutputStream().print("user@host " + state.getWorkingDirectory().getName() + ">");

        BufferedReader reader = state.getInputStreamReader();

        try {
            List<String> words = List.of(reader.readLine().trim().split("\\s"));

            String commandName = words.get(0);

            if (commandName.isEmpty()) {
                return;
            }

            Command command = commandsByName.get(commandName);

            if (command == null) {
                throw new CommandException("Unknown command \"" + commandName + "\"!");
            }

            command.execute(state, words.subList(1, words.size()));

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void run() {

        while (!state.isTerminate()) {
            try {
                acceptCommand();
            } catch (CommandException e) {
                state.getOutputStream().println(e.getMessage());
            }
        }
    }
}
