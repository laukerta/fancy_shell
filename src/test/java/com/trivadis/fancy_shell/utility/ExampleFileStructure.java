package com.trivadis.fancy_shell.utility;

import com.trivadis.fancy_shell.resource.Directory;
import com.trivadis.fancy_shell.resource.File;

public final class ExampleFileStructure {

    private static Directory root;

    private ExampleFileStructure() {
    }

    public static Directory getRoot() {

        if (root == null) {
            root = new Directory("/");

            root.addResource(new File("a.out"));
            root.addResource(new File("makefile"));

            Directory sourcesDirectory = new Directory("sources");
            sourcesDirectory.addResource(new File("main.c"));

            Directory inputDirectory = new Directory("input");
            inputDirectory.addResource(new File("gamepad.c"));
            inputDirectory.addResource(new File("gamepad.h"));

            sourcesDirectory.addResource(inputDirectory);

            root.addResource(sourcesDirectory);
        }

        return root;
    }
}
