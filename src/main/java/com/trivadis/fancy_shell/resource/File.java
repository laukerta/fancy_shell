package com.trivadis.fancy_shell.resource;

public class File extends Resource {

    public File(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isLink() {
        return false;
    }

    @Override
    public String printContent() {
        return getName();
    }
}
