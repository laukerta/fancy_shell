package com.trivadis.fancy_shell.resource;

public class Link extends Resource {

    public static Resource resolve(Resource resource) {

        while (resource.isLink()) {
            resource = ((Link) resource).getReference();
        }

        return resource;
    }

    private Resource resource;

    public Link(String name, Resource resource) {
        super(name);
        this.resource = resource;
    }

    public Resource getReference() {
        return resource;
    }

    public void setReference(Resource resource) {
        this.resource = resource;
    }

    @Override
    public boolean isDirectory() {
        return resource.isDirectory();
    }

    @Override
    public boolean isLink() {
        return true;
    }

    @Override
    public String printContent() {
        return resource.printContent();
    }
}
