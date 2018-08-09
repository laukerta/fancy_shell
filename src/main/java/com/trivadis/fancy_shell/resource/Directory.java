package com.trivadis.fancy_shell.resource;

import java.util.*;

public class Directory extends Resource {

    private Directory parent;
    private List<Resource> resources;

    public Directory(String name) {
        super(name);
        parent = this;
        resources = new ArrayList<>(Arrays.asList(
                new Link(".", this),
                new Link("..", parent)));
    }

    public static Optional<Resource> findResource(Directory rootDirectory, Directory workingDirectory, String path) {

        if (path.startsWith("/")) { // absolute path

            if (path.length() == 1) {
                return Optional.of(rootDirectory);
            }

            return rootDirectory.findResource(path.substring(1));
        }

        // relative path
        return workingDirectory.findResource(path);
    }

    public void addResource(Resource resource) {

        if (resource.isDirectory()) {
            addResource((Directory) resource);
            return;
        }

        resources.add(resource);
    }

    public void addResource(Directory directory) {

        Directory oldParent = directory.getParent();

        if (oldParent != null) {
            oldParent.removeResource(directory);
        }

        directory.setParent(this);

        resources.add(directory);
    }

    public Optional<Resource> findResource(String relativePath) {

        String[] pathElements = relativePath.split("/", 2);

        Optional<Resource> maybeResource = resources.stream()
                .filter(r -> pathElements[0].equals(r.getName()))
                .map(Link::resolve)
                .findFirst();

        if (pathElements.length == 1 || pathElements[1].isEmpty()) {
            return maybeResource;
        }

        return maybeResource
                .filter(Resource::isDirectory)
                .flatMap(r -> ((Directory) r).findResource(pathElements[1]));
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {

        this.parent = parent;

        Link parentLink = (Link) resources.stream()
                .filter(r -> "..".equals(r.getName()))
                .findFirst()
                .get();

        parentLink.setReference(parent);
    }

    public List<Resource> getResources() {
        return resources;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isLink() {
        return false;
    }

    public String printContent() {

        StringBuilder builder = new StringBuilder();

        Iterator<Resource> iterator = resources.iterator();

        builder.append(iterator.next().getName());

        while (iterator.hasNext()) {
            builder.append(' ').append(iterator.next().getName());
        }

        return builder.toString();
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }
}
