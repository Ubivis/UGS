package com.ubivismedia.extension;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ExtensionClassLoader {
    private final Map<String, Class<?>> loadedExtensions = new HashMap<>();

    public void loadExtension(File jarFile) throws Exception {
        URL url = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());

        // Load main class from the JAR (assuming it has a predefined entry class)
        // This should be configurable or read from a manifest
    }
}