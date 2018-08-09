package com.trivadis.fancy_shell.command;

import com.trivadis.fancy_shell.ShellState;

import java.util.List;
import java.util.function.BiConsumer;

public class VirtualCommand extends Command {

    private final BiConsumer<ShellState, List<String>> strategy;

    public VirtualCommand(String name, BiConsumer<ShellState, List<String>> strategy) {
        super(name);
        this.strategy = strategy;
    }

    @Override
    public void execute(ShellState shellState, List<String> arguments) {
        strategy.accept(shellState, arguments);
    }
}
