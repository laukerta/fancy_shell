package com.trivadis.fancy_shell.resource;

public abstract class Resource {

    private String name;

    public Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean isDirectory();

    public abstract boolean isLink();

    public abstract String printContent();

}
