package com.ubivismedia.data;

public class ExtensionData {
    private final String name;
    private final String version;
    private final String author;

    public ExtensionData(String name, String version, String author) {
        this.name = name;
        this.version = version;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }
}