package com.trivadis.fancy_shell.exception;

public class CommandException extends RuntimeException {

    public CommandException(String errorMessage) {
        super(errorMessage);
    }
}
